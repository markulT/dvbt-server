package com.osmos.server.banner.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBannerDto {
    private String id;
    private String fieldName;
    private Object value;
}
