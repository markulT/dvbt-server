package com.osmos.server.engineer;

import com.osmos.server.auth.exceptions.EmailAlreadyExistsException;
import com.osmos.server.engineer.dto.EngineerDto;
import com.osmos.server.repo.RoleRepo;
import com.osmos.server.repo.UserRepo;
import com.osmos.server.schema.Role;
import com.osmos.server.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EngineerService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

//    TODO: CRUD for engineers


    public EngineerDto create(EngineerDto engineerDto) {
        User existingUser = userRepo.getUserByEmail(engineerDto.getEmail());
        if (existingUser != null) {
            throw new EmailAlreadyExistsException("Engineer with that email already exists");
        }

        Role adminRole = roleRepo.findRoleByName("ROLE_ENGINEER");
        User newUser = User.builder()
                .roles(List.of(adminRole))
                .password(passwordEncoder.encode(engineerDto.getPassword()))
                .email(engineerDto.getEmail())
                .fullName(engineerDto.getFullName())
                .login(engineerDto.getLogin())
                .build();
        userRepo.save(newUser);
        return EngineerDto.builder()
                .login(newUser.getLogin())
                .fullName(newUser.getFullName())
                .email(newUser.getEmail())
                .build();
    }

    public List<User> getPage(int pageSize, int pageNumber) {
//        Page<User> userPage = userRepo.findAll(PageRequest.of(pageNumber, pageSize));
        Role role = roleRepo.findRoleByName("ROLE_ENGINEER");
        Page<User> userPage = userRepo.findAllByRolesContaining(role, PageRequest.of(pageNumber, pageSize));
//        List<User> userList = userRepo.findAllByRolesContaining(role);
        return userPage.getContent();
    }

    public long getLength() {
        Role engineerRole = roleRepo.findRoleByName("ROLE_ENGINEER");
        return userRepo.countAllByRolesContaining(engineerRole);
    }

    public boolean delete(UUID id) {
        User userExists = userRepo.findById(id).orElseThrow();
//        if (userExists.isEmpty()) {
//            throw new EngineerNotFoundException("Engineer with such id does not exist");
//        }
//        userRepo.delete(userExists.);
        userRepo.delete(userExists);
        return true;
    }

    public EngineerDto update(UUID id,String fieldName, Object value) {
        User user = userRepo.findById(id).orElseThrow();
        Field field = ReflectionUtils.findField(User.class, fieldName);
        field.setAccessible(true);
        ReflectionUtils.setField(field, user, value);
        return EngineerDto.builder()
                .login(user.getLogin())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .build();
    }


}
