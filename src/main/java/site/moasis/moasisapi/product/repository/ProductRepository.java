package site.moasis.moasisapi.product.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.moasis.moasisapi.product.entity.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductCode(String productCode);

    Slice<Product> findAllByNameContaining(String name, Pageable pageable);

    Boolean existsByProductNumber(String productNumber);
}
