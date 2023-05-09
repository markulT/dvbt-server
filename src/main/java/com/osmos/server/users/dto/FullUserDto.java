package com.osmos.server.users.dto;

import com.osmos.server.orders.dto.FullOrderDto;
import com.osmos.server.orders.dto.OrderDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FullUserDto {

    private String email;
    private String fullName;
    private List<OrderDto> orderList;

}
