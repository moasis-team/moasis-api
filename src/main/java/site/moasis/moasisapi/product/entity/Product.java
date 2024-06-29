package site.moasis.moasisapi.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.moasis.moasisapi.product.dto.PatchProductRequestDTO;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Product extends BaseEntity {
    private String name;

    private int price;

    private String category;

    private String imageUrl;

    private String details;

    private int quantity;

    @Column(unique = true)
    private String productCode;

    @Column(unique = true)
    private String productNumber;

    public Product updateEntity(PatchProductRequestDTO toUpdate, String imageUrl) {
        this.name = toUpdate.getName() != null ? toUpdate.getName() : this.name;
        this.price = toUpdate.getPrice() != null ? toUpdate.getPrice() : this.price;
        this.category = toUpdate.getCategory() != null ? toUpdate.getCategory() : this.category;
        this.imageUrl = imageUrl != null ? imageUrl : this.imageUrl;
        this.details = toUpdate.getDetails() != null ? toUpdate.getDetails() : this.details;
        this.quantity = toUpdate.getQuantity() != null ? toUpdate.getQuantity() : this.quantity;
        this.productNumber = toUpdate.getProductNumber() != null ? toUpdate.getProductNumber() : this.productNumber;

        return this;
    }
}
