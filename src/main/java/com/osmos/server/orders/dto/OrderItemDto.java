package com.osmos.server.orders.dto;

import com.osmos.server.orders.entities.OrderItem;
import com.osmos.server.products.ProductsRepo;
import com.osmos.server.products.dto.ProductDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemDto {

    private String productId;

    private int quantity;

//    private String orderId;

    public static OrderItemDto copy(OrderItem orderItem) {
        return OrderItemDto.builder()
                .productId(orderItem.getId().toString())
                .quantity(orderItem.getQuantity())
                .build();
    }


}
