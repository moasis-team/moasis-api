package site.moasis.moasisapi.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProductDto {
    private String name;
    private int price;
    private String category;
    private String imageUrl;
    private String details;
    private int quantity;
    private String productCode;
    private String productNumber;
}
