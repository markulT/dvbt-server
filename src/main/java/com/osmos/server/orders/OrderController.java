package com.osmos.server.orders;

import com.osmos.server.orders.dto.FullOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Boolean> create() {
        return ResponseEntity.ok(true);
    }

    @GetMapping("/page")
    public ResponseEntity<List<FullOrderDto>> getPage(@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber) {
        return ResponseEntity.ok(null);
    }

}
