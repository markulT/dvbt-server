package com.osmos.server.products;

import com.osmos.server.products.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepo extends JpaRepository<Category, UUID> {



}
