package com.osmos.server.repo;

import com.osmos.server.schema.Role;
import com.osmos.server.schema.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
    User getUserByEmail(String email);

    Page<User> findAll(@NonNull Pageable pageable);

    Page<User> findAllByRolesContaining(Role role, Pageable pageable);

    List<User> findAllByRolesContaining(Role role);

    long countAllByRolesContaining(Role role);

}
