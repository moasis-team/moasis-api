package site.moasis.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.api.exception.DuplicatedException;
import site.moasis.api.exception.NotFoundException;
import site.moasis.common.client.ImageClient;
import site.moasis.common.dto.CreateProductRequestDTO;
import site.moasis.common.dto.GetProductResponseDTO;
import site.moasis.common.dto.PatchProductRequestDTO;
import site.moasis.common.entity.Product;
import site.moasis.common.repository.ProductRepository;
import site.moasis.common.service.SlackAlertService;

import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageClient imageClient;
    private final SlackAlertService slackAlertService;

    @Transactional
    public Product createProduct(CreateProductRequestDTO createProductRequestDTO) {

        if (productRepository.existsByProductNumber(createProductRequestDTO.getProductNumber())) {
            throw new DuplicatedException("상품 번호가 이미 존재합니다");
        }

        try {
            String productCode = UUID.randomUUID().toString();
            String base64EncodedFile = Base64.getEncoder().encodeToString(createProductRequestDTO.getEncodedFile());
            String imageUrl = imageClient.uploadFile(base64EncodedFile);
            Product product = createProductRequestDTO.toEntity(productCode, imageUrl);

            productRepository.save(product);
            return product;
        } catch (Exception e) {
            slackAlertService.alertFailedProduct(
                createProductRequestDTO.getName(),
                createProductRequestDTO.getProductNumber(), "Save"
            );
            log.error("Product save failed for product number: {}", createProductRequestDTO.getProductNumber(), e);
            throw e;
        }
    }

    public GetProductResponseDTO getProduct(String productCode) {
        Product product = productRepository.findByProductCode(productCode).orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));
        return GetProductResponseDTO.of(product);
    }

    public Slice<GetProductResponseDTO> getProductList(String name, String category, String productNumber, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Slice<Product> response = productRepository.findByMultipleCriteria(name, category, productNumber, pageable);
        return GetProductResponseDTO.fromSlice(response);
    }

    @Transactional
    public Product updateProduct(String productCode, PatchProductRequestDTO patchProductRequestDTO) {
        Product product = productRepository.findByProductCode(productCode).orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));
        String imageUrl = updateImage(patchProductRequestDTO, product);
        return product.updateEntity(patchProductRequestDTO, imageUrl);
    }

    @Transactional
    public Product deleteProduct(String productCode) {
        Product product = productRepository.findByProductCode(productCode).orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));
        imageClient.deleteFile(product.getImageUrl());
        productRepository.delete(product);
        return product;
    }

    private String updateImage(PatchProductRequestDTO patchProductRequestDTO, Product product) {
        String oldImageUrl = product.getImageUrl();
        String imageUrl = oldImageUrl;
        try {
            if (patchProductRequestDTO.getEncodedFile() != null) {
                String base64EncodedFile = Base64.getEncoder().encodeToString(patchProductRequestDTO.getEncodedFile());
                imageUrl = imageClient.uploadFile(base64EncodedFile);
                imageClient.deleteFile(oldImageUrl);
            }
        } catch (Exception e) {
            slackAlertService.alertFailedProduct(product.getName(), product.getProductNumber(), "Update");
            log.error("Product image update failed for product number: {}", product.getProductNumber(), e);
            throw e;
        }
        return imageUrl;
    }
}

