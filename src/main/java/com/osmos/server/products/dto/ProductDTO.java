package com.osmos.server.products.dto;

import com.osmos.server.products.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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

    private double rangeInMeters;

    private List<ProductDTO> complementary;

    ProductDTO(Product product, String id) {
        this.name = product.getName();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.imgName = product.getImgName();
        this.category = CategoryDto.builder().name(product.getCategory().getName()).build();
        this.rangeInMeters = product.getRangeInMeters();
        this.id = id;
    }

    ProductDTO(Product product, UUID uuid) {
        this(product, uuid.toString());
    }

    public static ProductDTO basicClone(Product product) {
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
                .rangeInMeters(product.getRangeInMeters())
                .build();
    }

    public static ProductDTO clone(Product product) {
        ProductDTO productDTO = basicClone(product);
        System.out.println(product.getTitle());
        System.out.println(product.getCategory());
        if (product.getCategory() != null){
            productDTO.setComplementary(product.getCategory().getAdditionals().stream().map(ProductDTO::basicClone).toList());
        }
        return productDTO;
    }

    public static ProductDTO cloneAdditional(Product product) {
        return basicClone(product);
    }

}
