package com.osmos.server.orders.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateStatusRequest {

    private String orderId;
    private String status;

}
