package com.osmos.server.products.dto;

import com.osmos.server.products.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateShortProductDto {

    private String title;
    private String name;
    private double price;
    private String desc;
    private String imgName;

    public static CreateShortProductDto copy(Product product) {
        return CreateShortProductDto.builder()
                .desc(product.getDescription())
                .imgName(product.getImgName())
                .name(product.getName())
                .price(product.getPrice())
                .title(product.getTitle())
                .build();
    }

}
