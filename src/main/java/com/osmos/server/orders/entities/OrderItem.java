package com.osmos.server.orders.entities;

import com.osmos.server.products.entities.Product;
import com.osmos.server.schema.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "order_item")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderItem extends BaseEntity {

    @ManyToOne
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    private Order order;

}
