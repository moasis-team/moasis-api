package site.moasis.common.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.moasis.common.entity.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductCode(String productCode);

    @Query(
        value = "SELECT p FROM Product p WHERE " +
            "(:name IS NULL OR p.name LIKE CONCAT('%', :name, '%')) AND " +
            "(:category IS NULL OR p.category LIKE CONCAT('%', :category, '%')) AND " +
            "(:productNumber IS NULL OR p.productNumber LIKE CONCAT('%', :productNumber, '%'))"
    )
    Slice<Product> findByMultipleCriteria(
        @Param("name") String name,
        @Param("category") String category,
        @Param("productNumber") String productNumber,
        Pageable pageable
    );

    Boolean existsByProductNumber(String productNumber);
}
