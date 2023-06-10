package com.osmos.server.products.dto;

import lombok.Data;

@Data
public class UpdateProductDTO {
    private String id;
    private String fieldToChange;
    private Object fieldValue;
}
