package site.moasis.moasisapi.product.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.moasis.moasisapi.product.entity.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductCode(String productCode);

    @Query("SELECT p FROM Product p WHERE :name IS NULL OR p.name LIKE %:name%")
    Slice<Product> findAllByNameContaining(@Param("name") String name, Pageable pageable);

    Boolean existsByProductNumber(String productNumber);
}
