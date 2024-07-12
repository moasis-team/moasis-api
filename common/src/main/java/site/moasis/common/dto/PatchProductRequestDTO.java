package site.moasis.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PatchProductRequestDTO {
    private String name;
    private Integer price;
    private String details;
    private byte[] encodedFile;
    private String category;
    private Integer quantity;
    private String productNumber;
}
