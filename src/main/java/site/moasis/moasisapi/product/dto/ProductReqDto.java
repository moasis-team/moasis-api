package site.moasis.moasisapi.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductReqDto {
    private String name;
    private Long price;
    private String details;
    private byte[] image;
    private String category;
    private int quantity;
    private String productNumber;
}