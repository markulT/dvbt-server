package com.osmos.server.users.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private String email;
    private String fullName;
    private String id;

}
