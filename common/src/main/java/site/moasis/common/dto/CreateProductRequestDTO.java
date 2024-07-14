package site.moasis.common.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.moasis.common.entity.Product;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateProductRequestDTO {
    @NotBlank(message = "name은 필수입니다.")
    private String name;
    @Min(value = 0, message = "price는 0 이상이어야 합니다.")
    private int price;
    @NotBlank(message = "details는 필수입니다.")
    private String details;
    @NotNull(message = "encodedFile은 필수입니다.")
    @Size(max = 512*1024, message = "이미지 크기는 512kb 이하여야합니다")
    private byte[] encodedFile;
    @NotBlank(message = "category는 필수입니다.")
    private String category;
    @Min(value = 0, message = "quantity는 0 이상이어야 합니다.")
    private int quantity;
    @NotBlank(message = "productNumber는 필수입니다.")
    private String productNumber;

    public Product toEntity(String productCode, String imageUrl) {
        return Product.builder()
            .name(this.getName())
            .price(this.getPrice())
            .category(this.getCategory())
            .details(this.getDetails())
            .imageUrl(imageUrl)
            .quantity(this.getQuantity())
            .productCode(productCode)
            .productNumber(this.getProductNumber())
            .build();
    }
}
