package com.osmos.server.products;

import com.osmos.server.products.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductsRepo extends JpaRepository<Product, UUID> {

    Page<Product> findAll(Pageable pageable);
    Product getProductById(UUID uuid);

}
