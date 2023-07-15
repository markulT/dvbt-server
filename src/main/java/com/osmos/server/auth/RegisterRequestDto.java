package com.osmos.server.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDto {

    public String email;
    public String password;
    public String phone;
    public String fullName;

}
