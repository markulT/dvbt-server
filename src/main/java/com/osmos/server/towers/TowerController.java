package com.osmos.server.towers;

import com.osmos.server.responseDto.CreateEntity;
import com.osmos.server.responseDto.GetAll;
import com.osmos.server.responseDto.GetPage;
import com.osmos.server.towers.dto.TowerDto;
import com.osmos.server.towers.entities.Tower;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/towers")
@RequiredArgsConstructor
public class TowerController {

    private final TowerService towerService;

//    TODO: CRUD API

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CreateEntity<TowerDto>> create(@RequestBody() TowerDto towerDto) {
        return ResponseEntity.ok(
                CreateEntity.<TowerDto>builder()
                        .entity(towerService.create(towerDto))
                        .build()
        );
    }

    @GetMapping("/all")
    public ResponseEntity<GetAll<TowerDto>> getPage() {
        return ResponseEntity.ok(
                GetAll.<TowerDto>builder()
                        .list(towerService.getAll())
                        .build()
        );
    }

    @GetMapping("/page")
    public ResponseEntity<GetPage<TowerDto>> getPage(@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber) {

        List<TowerDto> towerList = towerService.getPage(pageSize, pageNumber);

        return ResponseEntity.ok(GetPage.<TowerDto>builder()
                .page(towerList)
                .length(towerService.getLength())
                .build());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id) {
        return ResponseEntity.ok(towerService.delete(UUID.fromString(id)));
    }

}
