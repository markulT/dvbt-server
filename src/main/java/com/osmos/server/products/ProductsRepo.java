package com.osmos.server.products;

import com.osmos.server.products.entities.Category;
import com.osmos.server.products.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductsRepo extends JpaRepository<Product, UUID> {

    Page<Product> findAll(Pageable pageable);
    Product getProductById(UUID uuid);
    Page<Product> findAllByCategory(Pageable pageable, Category category);
    List<Product> findAllByCategory(Category category);
    long countAllByCategory(Category category);

    @Query(value = "SELECT * FROM products WHERE range > :rating", nativeQuery = true)
    List<Product> findProductsByRangeInMeters(@Param("rating") double rating);

}
