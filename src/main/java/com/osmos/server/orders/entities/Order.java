package com.osmos.server.orders.entities;


import com.osmos.server.products.entities.Product;
import com.osmos.server.schema.BaseEntity;
import com.osmos.server.schema.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "orders")
public class Order extends BaseEntity {

    @ManyToOne
    private User orderedBy;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> productList;

    private double finalPrice;

    private String location;

    @OneToOne()
    private User engineer;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;



}
