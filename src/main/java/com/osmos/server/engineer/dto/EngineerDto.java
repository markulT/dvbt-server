package com.osmos.server.engineer.dto;

import com.osmos.server.orders.dto.OrderDto;
import com.osmos.server.schema.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EngineerDto {

    private String email;
    private String fullName;
    private String id;

    public static EngineerDto copyFromEntity(User user) {
        if (user==null) {return null;}
        return EngineerDto.builder()
                .email(user.getEmail())
                .fullName(user.getFullName())
                .id(user.getId().toString())
                .build();
    }

}
