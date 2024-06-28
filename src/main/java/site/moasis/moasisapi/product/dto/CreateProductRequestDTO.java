package site.moasis.moasisapi.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateProductRequestDTO {
    @NotBlank
    private String name;
    @Min(0)
    private int price;
    @NotBlank
    private String details;
    @NotBlank
    private byte[] encodedFile;
    @NotBlank
    private String category;
    @Min(0)
    private int quantity;
    @NotBlank
    private String productNumber;
}
