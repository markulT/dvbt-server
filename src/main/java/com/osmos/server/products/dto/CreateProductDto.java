package com.osmos.server.products.dto;


import com.osmos.server.products.entities.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProductDto {

    private String name;

    private String title;

    private double price;

    private String imgName;

    private Category category;

    private double length;

    private String chanel;

    private double amplification;

    private double outputImpedance;

    private String currentConsumption;

    private String packagement;

    private String description;

}
