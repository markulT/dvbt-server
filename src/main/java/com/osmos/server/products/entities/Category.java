package com.osmos.server.products.entities;

import com.osmos.server.schema.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="category")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "product_list")
    @OneToMany(mappedBy = "category")
    private List<Product> productList;

}
