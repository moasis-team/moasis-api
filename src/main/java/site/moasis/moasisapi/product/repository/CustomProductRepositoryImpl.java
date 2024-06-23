package site.moasis.moasisapi.product.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import site.moasis.moasisapi.product.entity.Product;

public class CustomProductRepositoryImpl implements CustomProductRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Product findByProductCode(String productCode) {
        return entityManager.createQuery("SELECT p FROM Product p WHERE p.productCode = :productCode", Product.class)
            .setParameter("productCode", productCode)
            .getSingleResult();
    }
}
