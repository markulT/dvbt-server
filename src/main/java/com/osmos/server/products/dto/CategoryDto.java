package com.osmos.server.products.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryDto {

    private String name;
    private String id;
    private List<ProductDTO> additionals;

}
