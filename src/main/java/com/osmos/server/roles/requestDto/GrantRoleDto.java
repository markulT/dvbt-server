package com.osmos.server.roles.requestDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GrantRoleDto {
    private String rolename;
    private String email;
}
