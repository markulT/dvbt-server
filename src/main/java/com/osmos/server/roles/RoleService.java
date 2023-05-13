package com.osmos.server.roles;

import com.osmos.server.repo.RoleRepo;
import com.osmos.server.repo.UserRepo;
import com.osmos.server.roles.exceptions.RolenameAlreadyExists;
import com.osmos.server.schema.Role;
import com.osmos.server.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepo roleRepo;
    private final UserRepo userRepo;

    public void add(String rolename) {
        Role role = Role.builder()
                .name(rolename)
                .build();
        if(roleRepo.findRoleByName(rolename) != null) {
            throw new RolenameAlreadyExists("Role with name " + rolename + " already exists");
        }
        roleRepo.save(role);
    }

    public void grantRole(String rolename, String username) {
        Role role = roleRepo.findRoleByName(rolename);
        User user = userRepo.getUserByEmail(username);
        user.getRoles().add(role);
        userRepo.save(user);
    }

    public boolean isUserEngineer(User user) {
        return user.getRoles().contains(roleRepo.findRoleByName("ROLE_ENGINEER"));
    }

    public boolean isUserEngineer(String id) {
        User user = userRepo.findById(UUID.fromString(id)).orElseThrow();
        return isUserEngineer(user);
    }

    public boolean isUserAdmin(User user) {
        return user.getRoles().contains(roleRepo.findRoleByName("ROLE_ADMIN"));
    }

}
