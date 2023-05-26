package com.osmos.server.orders.dto;

import com.osmos.server.engineer.dto.EngineerDto;
import com.osmos.server.orders.entities.Order;
import com.osmos.server.orders.entities.OrderStatus;
import com.osmos.server.products.dto.ProductDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FullOrderDto {
    private String location;
    private String orderedBy;
    private String orderedFullName;
    private double finalPrice;
    private List<OrderItemDto> productList;
    private OrderStatus orderStatus;
    private EngineerDto engineer;

    public static FullOrderDto copyFromEntity(Order order) {
        return FullOrderDto.builder()
                .location(order.getLocation())
                .orderedBy(order.getOrderedBy().getId().toString())
                .orderedFullName(order.getOrderedBy().getFullName())
                .finalPrice(order.getFinalPrice())
                .productList(order.getProductList().stream().map(OrderItemDto::copy).toList())
                .orderStatus(order.getOrderStatus())
                .engineer(EngineerDto.copyFromEntity(order.getEngineer()))
                .build();
    }

}
