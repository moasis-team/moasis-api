package site.moasis.moasisapi.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.moasis.moasisapi.product.dto.ProductReqDto;
import site.moasis.moasisapi.product.entity.Product;
import site.moasis.moasisapi.product.repository.ProductRepository;

import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageService imageService;

    public String createProduct(ProductReqDto productReqDto) {
        String productCode = UUID.randomUUID().toString();
        String encodedFileBase64 = Base64.getEncoder().encodeToString(productReqDto.getEncodedFile());
        String imageUrl = imageService.uploadFile(encodedFileBase64);
        Product product = Product.builder()
            .name(productReqDto.getName())
            .price(productReqDto.getPrice())
            .category(productReqDto.getCategory())
            .imageUrl(imageUrl)
            .details(productReqDto.getDetails())
            .quantity(productReqDto.getQuantity())
            .productNumber(productReqDto.getProductNumber())
            .build();
        productRepository.save(product);
        return productCode;
    }
}

