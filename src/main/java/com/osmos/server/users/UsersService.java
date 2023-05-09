package com.osmos.server.users;

import com.osmos.server.orders.dto.OrderDto;
import com.osmos.server.repo.UserRepo;
import com.osmos.server.schema.User;
import com.osmos.server.users.dto.FullUserDto;
import com.osmos.server.users.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UserRepo userRepo;

    public boolean delete(UUID id) {
        User user = userRepo.findById(id).orElseThrow();
        userRepo.delete(user);
        return true;
    }
    public List<UserDto> getPage(int pageNumber, int pageSize) {
        List<User> userPage = userRepo.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
        return userPage.stream().map(user -> UserDto.builder()
                .fullName(user.getFullName())
                .email(user.getEmail())
                .id(user.getId().toString())
                .build()).toList();
    }

    public List<UserDto> getClientPage(int pageNumber, int pageSize) {
        List<User> userPage = userRepo.findAll(PageRequest.of(pageNumber, pageSize)).getContent();

        return userPage.stream().map(user -> UserDto.builder()
                .fullName(user.getFullName())
                .email(user.getEmail())
                .build()).toList();
    }
    public long getLength() {
        return userRepo.count();
    }

    public FullUserDto getFullUser(UUID id) {
        User user = userRepo.findById(id).orElseThrow();

        return FullUserDto.builder()
                .fullName(user.getFullName())
                .email(user.getEmail())
                .orderList(user.getOrders().stream().map(order -> OrderDto.builder()
                        .finalPrice(order.getFinalPrice())
                        .location(order.getLocation())
                        .orderedBy(user.getId().toString())
                        .orderedFullName(user.getFullName())
                        .build()).toList())
                .build();
    }

}
