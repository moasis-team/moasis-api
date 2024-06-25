package site.moasis.moasisapi.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
