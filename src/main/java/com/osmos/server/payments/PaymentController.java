package com.osmos.server.payments;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @PostMapping("/action")
    public void webhook(@RequestBody Object body) {
        System.out.println("PAYMENT");
        System.out.println(body);
    }

}
