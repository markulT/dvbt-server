package com.osmos.server.towers.dto;

import com.osmos.server.towers.entities.Tower;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TowerDto {
    private String name;
    private double latitude;
    private double longitude;
    private double rangeInMeters;
    private String id;

    public static TowerDto cloneFromEntity(Tower tower) {
        return TowerDto.builder()
                .id(tower.getId().toString())
                .name(tower.getName())
                .longitude(tower.getLongitude())
                .latitude(tower.getLatitude())
                .rangeInMeters(tower.getRangeInMeters())
                .build();
    }

}
