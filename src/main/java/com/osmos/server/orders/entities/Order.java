package com.osmos.server.orders.entities;


import com.osmos.server.products.entities.Product;
import com.osmos.server.schema.BaseEntity;
import com.osmos.server.schema.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @ManyToOne
    private User orderedBy;

    @ManyToMany
    private List<Product> productList;

    private double finalPrice;

    private String location;

}
