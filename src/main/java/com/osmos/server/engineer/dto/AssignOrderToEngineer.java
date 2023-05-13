package com.osmos.server.engineer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssignOrderToEngineer {
    private String orderId;
    private String engineerId;
}
