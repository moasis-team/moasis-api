package site.moasis.moasisapi.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.moasisapi.client.ImageClient;
import site.moasis.moasisapi.common.exception.DuplicatedException;
import site.moasis.moasisapi.common.exception.NotFoundException;
import site.moasis.moasisapi.product.dto.CreateProductRequestDTO;
import site.moasis.moasisapi.product.dto.GetProductResponseDTO;
import site.moasis.moasisapi.product.entity.Product;
import site.moasis.moasisapi.product.repository.ProductRepository;

import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageClient imageClient;

    @Transactional
    public String createProduct(CreateProductRequestDTO createProductRequestDTO) {

        if (productRepository.existsByProductNumber(createProductRequestDTO.getProductNumber())) {
            throw new DuplicatedException("상품 번호가 이미 존재합니다");
        }

        String productCode = UUID.randomUUID().toString();
        String base64EncodedFile = Base64.getEncoder().encodeToString(createProductRequestDTO.getEncodedFile());
        String imageUrl = imageClient.uploadFile(base64EncodedFile);
        Product product = createProductRequestDTO.toEntity(productCode, imageUrl);

        productRepository.save(product);
        return productCode;
    }

    public GetProductResponseDTO getProduct(String productCode) {
        Product product = productRepository.findByProductCode(productCode).orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));
        return GetProductResponseDTO.of(product);
    }

    public Slice<GetProductResponseDTO> getProductList(String name, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Slice<Product> response = productRepository.findAllByNameContaining(name, pageable);
        return GetProductResponseDTO.fromSlice(response);
    }
}

