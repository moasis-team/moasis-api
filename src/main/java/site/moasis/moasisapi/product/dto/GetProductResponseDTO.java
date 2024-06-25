package site.moasis.moasisapi.product.dto;

import lombok.Builder;
import lombok.Getter;

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
}
