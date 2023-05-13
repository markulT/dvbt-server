package com.osmos.server.orders.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssignEngineerToOrderRequest {
    private String orderId;
    private String engineerId;
}
