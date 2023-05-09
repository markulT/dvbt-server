package com.osmos.server.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDTO {

    private String refreshToken;
    private String accessToken;
    private String email;

}
