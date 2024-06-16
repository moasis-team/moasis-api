package site.moasis.moasisapi.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.moasis.moasisapi.product.repository.ProductRepository;
import site.moasis.moasisapi.product.dto.ProductReqDto;
import site.moasis.moasisapi.product.entity.Product;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageService imageService;

    public String createProduct(ProductReqDto productReqDto) {
        String productCode = UUID.randomUUID().toString();
        String imageUrl = imageService.uploadFile(productReqDto.getEncodedFile());
        Product product = Product.builder()
                .name(productReqDto.getName())
                .price(productReqDto.getPrice())
                .category(productReqDto.getCategory())
                .imageUrl(imageUrl)
                .details(productReqDto.getDetails())
                .quantity(productReqDto.getQuantity())
                .productCode(productCode)
                .productNumber(productReqDto.getProductNumber())
                .build();
        productRepository.save(product);
        return productCode;
    }
}

