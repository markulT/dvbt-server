package com.osmos.server.users;

import com.osmos.server.responseDto.GetPage;
import com.osmos.server.users.dto.FullUserDto;
import com.osmos.server.users.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class UsersController {

    private final UsersService usersService;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id) {
        return ResponseEntity.ok(usersService.delete(UUID.fromString(id)));
    }

    @GetMapping("/page")
    public ResponseEntity<GetPage<UserDto>> getPage(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) {
        List<UserDto> userPage = usersService.getPage(pageNumber, pageSize);
        long length = usersService.getLength();
        return ResponseEntity.ok(GetPage.<UserDto>builder()
                .page(userPage)
                .length(length)
                .build());
    }

    @GetMapping("/userInfo/{id}")
    public ResponseEntity<FullUserDto> getUserInfo(@PathVariable("id") String id) {
        return ResponseEntity.ok(usersService.getUserFull(UUID.fromString(id)));
    }

    @GetMapping("/userShort/{id}")
    public ResponseEntity<?> getUserShort(@PathVariable("id") String id) {
        return ResponseEntity.ok(usersService.getUserShort(id));
    }


}
