package site.moasis.moasisapi.product.repository;

import site.moasis.moasisapi.product.entity.Product;

public interface CustomProductRepository {
    Product findByProductCode(String productCode);
}
