package com.osmos.server.engineer;

import com.osmos.server.engineer.dto.EngineerDto;
import com.osmos.server.engineer.dto.UpdateEngineerDto;
import com.osmos.server.responseDto.GetPage;
import com.osmos.server.schema.User;
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

    @PostMapping("/create")
    public ResponseEntity<EngineerDto> create(@RequestBody() EngineerDto engineerDto) {
        return ResponseEntity.ok(engineerService.create(engineerDto));
    }

    @GetMapping("/page")
    public ResponseEntity<GetPage<EngineerDto>> getPage(@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber) {
        List<EngineerDto> userList = engineerService.getPage(pageSize, pageNumber).stream().map((user) -> EngineerDto.builder()
                        .fullName(user.getFullName())
                        .email(user.getEmail())
                        .login(user.getLogin())
                        .build())
                .toList();
        return ResponseEntity.ok(GetPage.<EngineerDto>builder()
                        .length()
                .build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(engineerService.delete(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EngineerDto> update(@RequestBody() UpdateEngineerDto engineerDto) {
        return ResponseEntity.ok(engineerService.update(UUID.fromString(engineerDto.getId()), engineerDto.getFieldName(), engineerDto.getValue()));
    }

}
