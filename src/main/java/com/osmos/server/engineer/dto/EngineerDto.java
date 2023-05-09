package com.osmos.server.engineer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EngineerDto {

    private String login;
    private String password;
    private String email;
    private String fullName;



}
