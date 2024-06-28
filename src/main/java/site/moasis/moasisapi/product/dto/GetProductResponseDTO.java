package site.moasis.moasisapi.product.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;
import site.moasis.moasisapi.product.entity.Product;

@Getter
@Builder
public class GetProductResponseDTO {
    private String name;
    private int price;
    private String category;
    private String imageUrl;
    private String details;
    private int quantity;
    private String productCode;
    private String productNumber;

    public static Slice<GetProductResponseDTO> fromSlice(Slice<Product> products) {
        return products.map(product -> GetProductResponseDTO.builder()
            .name(product.getName())
            .price(product.getPrice())
            .category(product.getCategory())
            .imageUrl(product.getImageUrl())
            .details(product.getDetails())
            .quantity(product.getQuantity())
            .productCode(product.getProductCode())
            .productNumber(product.getProductNumber())
            .build()
        );
    }

    public static GetProductResponseDTO fromEntity(Product product) {
        return GetProductResponseDTO.builder()
            .name(product.getName())
            .price(product.getPrice())
            .category(product.getCategory())
            .imageUrl(product.getImageUrl())
            .details(product.getDetails())
            .quantity(product.getQuantity())
            .productCode(product.getProductCode())
            .productNumber(product.getProductNumber())
            .build();
    }
}
