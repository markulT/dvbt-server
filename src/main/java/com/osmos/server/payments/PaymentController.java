package com.osmos.server.payments;

import com.osmos.server.payments.dto.PaymentIntentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/action")
    public void webhook(@RequestBody Object body) throws NoSuchFieldException {
        Field field = body.getClass().getField("data");
        System.out.println(field);
//        paymentService.confirmPayment(body.data.clientSecret);
    }

}
