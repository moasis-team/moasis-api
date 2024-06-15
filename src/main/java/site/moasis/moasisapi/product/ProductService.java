package site.moasis.moasisapi.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.moasis.moasisapi.product.dto.ProductReqDto;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public String createProduct(ProductReqDto productReqDto) {
        String productCode = UUID.randomUUID().toString();

        Product product = Product.builder()
                .name(productReqDto.getName())
                .price(productReqDto.getPrice())
                .category(productReqDto.getCategory())
                .imageId("") // [Todo] 이미지 서비스 연동
                .details(productReqDto.getDetails())
                .quantity(productReqDto.getQuantity())
                .productCode(productCode)
                .productNumber(productReqDto.getProductNumber())
                .build();

        productRepository.save(product);
        return productCode;
    }
}

