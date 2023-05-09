package com.osmos.server.products.dto;

import com.osmos.server.products.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String name;

    private String title;

    private double price;

    private String imgName;

    private CategoryDto category;

    private String id;

    private double length;

    private String chanel;

    private double amplification;

    private double outputImpedance;

    private String currentConsumption;

    private String packagement;

    private String description;

    ProductDTO(Product product, String id) {
        this.name = product.getName();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.imgName = product.getImgName();
        this.category = CategoryDto.builder().name(product.getCategory().getName()).build();
        this.id = id;
    }

    ProductDTO(Product product, UUID uuid) {
        this(product, uuid.toString());
    }

    public static ProductDTO clone(Product product) {
        return ProductDTO.builder()
                .name(product.getName())
                .packagement(product.getPackagement())
                .category(CategoryDto.builder().name(product.getName()).build())
                .outputImpedance(product.getOutputImpedance())
                .currentConsumption(product.getCurrentConsumption())
                .imgName(product.getImgName())
                .amplification(product.getAmplification())
                .chanel(product.getChanel())
                .title(product.getTitle())
                .id(product.getId().toString())
                .price(product.getPrice())
                .length(product.getLength())
                .description(product.getDescription())
                .build();
    }

}
