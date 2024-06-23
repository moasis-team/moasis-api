package site.moasis.moasisapi.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.moasis.moasisapi.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, CustomProductRepository {
    Product findByProductCode(String productCode);
}
