package com.osmos.server.orders.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateOrderDto {
    private String location;
    private List<String> productList;
}
