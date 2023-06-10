package com.osmos.server.products.entities;

import com.osmos.server.schema.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Product extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private double price;

    @Column(name = "description")
    private String description;

    @Column(name = "img_name")
    private String imgName;

    @ManyToOne
    private Category category;

    @Column(name = "length")
    private double length;

    @Column(name = "chanel")
    private String chanel;

    @Column(name = "amplification")
    private double amplification;

    @Column(name = "outputImpedance")
    private double outputImpedance;

    @Column(name = "currentConsumption")
    private String currentConsumption;

    @Column(name = "packagement")
    private String packagement;

    @Column(name = "range", columnDefinition = "double default 0.0")
    private double rangeInMeters;

    public static Product copy() {
        return Product.builder().build();
    }

}
