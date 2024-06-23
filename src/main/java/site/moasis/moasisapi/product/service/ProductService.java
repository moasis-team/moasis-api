package site.moasis.moasisapi.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.moasis.moasisapi.common.exception.BadRequestException;
import site.moasis.moasisapi.common.exception.NotFoundException;
import site.moasis.moasisapi.common.service.ImageService;
import site.moasis.moasisapi.product.dto.ReqCreateProductDto;
import site.moasis.moasisapi.product.dto.ResGetProductDto;
import site.moasis.moasisapi.product.entity.Product;
import site.moasis.moasisapi.product.repository.ProductRepository;

import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageService imageService;

    public String createProduct(ReqCreateProductDto reqCreateProductDto) throws BadRequestException {
        String productCode = UUID.randomUUID().toString();
        String base64EncodedFile = Base64.getEncoder().encodeToString(reqCreateProductDto.getEncodedFile());
        String imageUrl = imageService.uploadFile(base64EncodedFile);
        try {
            Product product = Product.builder()
                .name(reqCreateProductDto.getName())
                .price(reqCreateProductDto.getPrice())
                .category(reqCreateProductDto.getCategory())
                .imageUrl(imageUrl)
                .details(reqCreateProductDto.getDetails())
                .quantity(reqCreateProductDto.getQuantity())
                .productCode(productCode)
                .productNumber(reqCreateProductDto.getProductNumber())
                .build();
            productRepository.save(product);
            return productCode;
        } catch (Exception e) {
            throw new BadRequestException("상품 등록에 실패했습니다.");
        }
    }

    public ResGetProductDto getProduct(String productCode) throws NotFoundException {
        try {
            Product product = productRepository.findByProductCode(productCode);
            return ResGetProductDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .category(product.getCategory())
                .imageUrl(product.getImageUrl())
                .details(product.getDetails())
                .quantity(product.getQuantity())
                .productCode(product.getProductCode())
                .productNumber(product.getProductNumber())
                .build();
        } catch (Exception e) {
            throw new NotFoundException("상품을 찾을 수 없습니다.");
        }
    }
}

