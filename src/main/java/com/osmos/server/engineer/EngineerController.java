package com.osmos.server.engineer;

import com.osmos.server.engineer.dto.AssignOrderToEngineer;
import com.osmos.server.engineer.dto.EngineerDto;
import com.osmos.server.engineer.dto.UpdateEngineerDto;
import com.osmos.server.responseDto.GetPage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/engineer")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class EngineerController {

    private final EngineerService engineerService;

//    TODO: CRUD API

//    @PostMapping("/create")
//    public ResponseEntity<CreateEntity<EngineerDto>> create(@RequestBody() EngineerDto engineerDto) {
//        return ResponseEntity.ok(
//                CreateEntity.<EngineerDto>builder()
//                        .entity(engineerService.create(engineerDto))
//                        .build()
//        );
//    }

    @GetMapping("/page")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<GetPage<EngineerDto>> getPage(@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber) {
        List<EngineerDto> userList = engineerService.getPage(pageSize, pageNumber).stream().map(EngineerDto::copyFromEntity)
                .toList();
        return ResponseEntity.ok(GetPage.<EngineerDto>builder()
                        .page(userList)
                        .length(engineerService.getLength())
                .build());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Boolean> delete(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(engineerService.delete(id));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<EngineerDto> update(@RequestBody() UpdateEngineerDto engineerDto) {
        return ResponseEntity.ok(engineerService.update(UUID.fromString(engineerDto.getId()), engineerDto.getFieldName(), engineerDto.getValue()));
    }

    @PostMapping("/assign")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> assign(@RequestBody() AssignOrderToEngineer assignOrderToEngineer) {
        return ResponseEntity.ok(engineerService.assignOrderToEngineer(assignOrderToEngineer.getOrderId(), assignOrderToEngineer.getEngineerId()));

    }

}
