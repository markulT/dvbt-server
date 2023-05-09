package com.osmos.server.towers;

import com.osmos.server.towers.dto.TowerDto;
import com.osmos.server.towers.entities.Tower;
import com.osmos.server.towers.exceptions.TowerAlredyExists;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TowerService {

    private final TowerRepo towerRepo;

    public TowerDto create(TowerDto towerDto) {
        Tower tower = towerRepo.findByName(towerDto.getName());
        if (tower !=null) {
            throw new TowerAlredyExists("Tower with such name already exists");
        }
        Tower newTower = Tower.builder()
                .name(towerDto.getName())
                .latitude(towerDto.getLatitude())
                .longitude(towerDto.getLongitude())
                .rangeInMeters(towerDto.getRangeInMeters())
                .build();
        towerRepo.save(newTower);
        return TowerDto.cloneFromEntity(newTower);
    }

    public long getLength() {
        return towerRepo.count();
    }

    public List<TowerDto> getAll() {
        return towerRepo.findAll().stream().map(TowerDto::cloneFromEntity).toList();
    }

    public boolean delete(UUID id) {
        Tower tower = towerRepo.findById(id).orElseThrow();
        towerRepo.delete(tower);
        return true;
    }

    public List<TowerDto> getPage(int pageSize, int pageNumber) {
        return towerRepo.findAll(PageRequest.of(pageSize, pageNumber)).getContent().stream().map(TowerDto::cloneFromEntity).toList();
    }

}
