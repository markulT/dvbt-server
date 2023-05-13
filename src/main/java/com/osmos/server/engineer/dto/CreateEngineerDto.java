package com.osmos.server.engineer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateEngineerDto {
    private String password;
    private String fullName;
    private String email;
    private String login;
}
