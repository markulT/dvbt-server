package com.osmos.server.orders.dto;

import com.osmos.server.orders.entities.Order;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {

    private String location;
    private String orderedBy;
    private String orderedFullName;
    private double finalPrice;
    private String id;

    public static OrderDto copyFromEntity(Order order) {

        return OrderDto.builder()
                .location(order.getLocation())
                .orderedFullName(order.getOrderedBy().getFullName())
                .orderedBy(order.getOrderedBy().getId().toString())
                .finalPrice(order.getFinalPrice())
                .id(order.getId().toString())
                .build();
    }

}
