package com.osmos.server.test;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/task1")
    public String task1() {
        return "task1 completed";
    }

    @GetMapping("/task2")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String task2() {
        return "task2 completed";
    }
}
