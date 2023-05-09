package com.osmos.server.towers;

import com.osmos.server.towers.entities.Tower;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TowerRepo extends JpaRepository<Tower, UUID> {

    Tower findByName(String name);

    Page<Tower> findAll(Pageable pageable);
}
