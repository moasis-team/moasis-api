package site.moasis.moasisapi.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReqCreateProductDto {
    private String name;
    private int price;
    private String details;
    private byte[] encodedFile;
    private String category;
    private int quantity;
    private String productNumber;
}
