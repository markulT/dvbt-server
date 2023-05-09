package com.osmos.server.repo;

import com.osmos.server.schema.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepo extends JpaRepository<Role, UUID> {
    Role findRoleByName(String name);


}
