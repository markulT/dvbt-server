package com.osmos.server.orders.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {
    protected String location;
    protected String orderedBy;
    protected String orderedFullName;
    protected double finalPrice;
}
