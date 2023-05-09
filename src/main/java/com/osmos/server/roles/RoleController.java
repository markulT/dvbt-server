package com.osmos.server.roles;

import com.osmos.server.roles.requestDto.GrantRoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody RoleRequestDto body) {
            roleService.add(body.getName());
            return ResponseEntity.ok("Success");
    }

    @PostMapping("/grantRole")
    public ResponseEntity grantRole(@RequestBody GrantRoleDto body) {
        roleService.grantRole(body.getRolename(), body.getEmail());
        return ResponseEntity.ok("Success");
    }

}
