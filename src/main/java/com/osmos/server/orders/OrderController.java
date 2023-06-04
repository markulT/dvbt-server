package com.osmos.server.orders;

import com.osmos.server.orders.dto.*;
import com.osmos.server.orders.entities.OrderStatus;
import com.osmos.server.payments.Currency;
import com.osmos.server.payments.PaymentService;
import com.osmos.server.payments.dto.PaymentIntentResponse;
import com.osmos.server.responseDto.CreateEntity;
import com.osmos.server.responseDto.GetPage;
import com.osmos.server.responseDto.GetSingle;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentIntentResponse> create(@RequestBody() CreateOrderDto createOrderDto) {
        try {
            FullOrderDto fullOrderDto = orderService.create(createOrderDto);
            PaymentIntent paymentIntent = paymentService.intentPayment((long) fullOrderDto.getFinalPrice(), Currency.UAH);
//            return ResponseEntity.ok(CreateEntity.<FullOrderDto>builder()
//                    .entity(fullOrderDto).build());
            return ResponseEntity.ok(PaymentIntentResponse.builder()
                    .clientSecret(paymentIntent.getClientSecret()).build());
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(null);
        }
    }

    @GetMapping("/page")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<GetPage<OrderDto>> getPage(@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber) {
        return ResponseEntity.ok(GetPage.<OrderDto>builder()
                .page(orderService.getPage(pageNumber, pageSize))
                .length(orderService.getLength())
                .build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<GetSingle<FullOrderDto>> getOrder(@PathVariable("id") String id) {

        return ResponseEntity.ok(GetSingle.<FullOrderDto>builder()
                .item(orderService.getOrderDetails(id))
                .build());
    }

    @PutMapping("/changeStatus")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<GetSingle<FullOrderDto>> updateStatus(@RequestBody() UpdateStatusRequest updateStatusRequest) {
        return ResponseEntity.ok(GetSingle.<FullOrderDto>builder()
                .item(orderService.updateStatus(updateStatusRequest.getOrderId(), OrderStatus.valueOf(updateStatusRequest.getStatus())))
                .build());
    }

    @PutMapping("/assignEngineer")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<GetSingle<FullOrderDto>> assingEngineer(@RequestBody() AssignEngineerToOrderRequest assignEngineerToOrderRequest) {
        return ResponseEntity.ok(GetSingle.<FullOrderDto>builder()
                .item(orderService.assignEngineerToOrder(assignEngineerToOrderRequest.getOrderId(), assignEngineerToOrderRequest.getEngineerId()))
                .build());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") String id) {
        return ResponseEntity.ok(orderService.deleteOrder(id));
    }

}
