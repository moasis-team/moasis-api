package site.moasis.common.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Slice;
import site.moasis.common.entity.Product;

import java.time.Instant;

public class ProductDto {

    @Getter
    @Builder
    @ToString
    public static class RegisterRequest {
        @NotBlank(message = "name은 필수입니다.")
        private String name;

        @Min(value = 0, message = "price는 0 이상이어야 합니다.")
        private int price;

        @NotBlank(message = "details는 필수입니다.")
        private String details;

        @NotNull(message = "encodedFile은 필수입니다.")
        @Size(max = 512 * 1024, message = "이미지 크기는 512kb 이하여야합니다")
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

    @Getter
    @Builder
    @ToString
    public static class GetResponse {
        private String name;
        private int price;
        private String category;
        private String imageUrl;
        private String details;
        private int quantity;
        private String productCode;
        private String productNumber;
        private Instant updatedAt;

        public static Slice<GetResponse> fromSlice(Slice<Product> products) {
            return products.map(product -> GetResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .category(product.getCategory())
                .imageUrl(product.getImageUrl())
                .details(product.getDetails())
                .quantity(product.getQuantity())
                .productCode(product.getProductCode())
                .productNumber(product.getProductNumber())
                .updatedAt(product.getUpdatedAt())
                .build()
            );
        }

        public static GetResponse of(Product product) {
            return GetResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .category(product.getCategory())
                .imageUrl(product.getImageUrl())
                .details(product.getDetails())
                .quantity(product.getQuantity())
                .productCode(product.getProductCode())
                .productNumber(product.getProductNumber())
                .updatedAt(product.getUpdatedAt())
                .build();
        }
    }


    @Getter
    @Builder
    @ToString
    public static class UpdateRequest {
        private String name;
        private Integer price;
        private String details;
        private byte[] encodedFile;
        private String category;
        private Integer quantity;
        private String productNumber;
    }

    @Getter
    @Builder
    @ToString
    public static class UpdateResponse {
        private String name;
        private int price;
        private String category;
        private String imageUrl;
        private String details;
        private int quantity;
        private String productCode;
        private String productNumber;

        public static UpdateResponse of(Product product) {
            return UpdateResponse.builder()
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
}
