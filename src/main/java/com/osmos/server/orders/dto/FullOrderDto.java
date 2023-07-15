package com.osmos.server.orders.dto;

import com.osmos.server.engineer.dto.EngineerDto;
import com.osmos.server.orders.entities.Order;
import com.osmos.server.orders.entities.OrderStatus;
import com.osmos.server.products.dto.ProductDTO;
import lombok.Builder;
import lombok.Data;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Data
@Builder
public class FullOrderDto {
    private String location;
    private String orderedBy;
    private String orderedFullName;
    private double finalPrice;
    private List<OrderItemDto> productList;
    private OrderStatus orderStatus;
    private Optional<String> id;

    @Nullable
    private EngineerDto engineer;

    public static FullOrderDto copyFromEntity(Order order) {
        System.out.println("product list : ");
        System.out.println(order.getProductList());
        return FullOrderDto.builder()
                .location(order.getLocation())
                .orderedBy(order.getOrderedBy().getId().toString())
                .orderedFullName(order.getOrderedBy().getFullName())
                .finalPrice(order.getFinalPrice())
                .productList(order.getProductList().stream().map(OrderItemDto::copy).toList())
                .orderStatus(order.getOrderStatus())
                .engineer(EngineerDto.copyFromEntity(order.getEngineer()))
                .id(Optional.ofNullable(order.getId().toString()))
                .build();
    }

}
