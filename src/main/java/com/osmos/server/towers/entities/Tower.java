package com.osmos.server.towers.entities;

import com.osmos.server.schema.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "tower")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tower extends BaseEntity {
    private String name;
    private double latitude;
    private double longitude;
    private double rangeInMeters;
}
