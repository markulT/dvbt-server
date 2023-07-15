package com.osmos.server.users.dto;

import com.osmos.server.schema.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private String email;
    private String fullName;
    private String id;
    private String phone;

    public static UserDto copyFromEntity(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .fullName(user.getFullName())
                .id(user.getId().toString())
                .phone(user.getPhone())
                .build();
    }

}
