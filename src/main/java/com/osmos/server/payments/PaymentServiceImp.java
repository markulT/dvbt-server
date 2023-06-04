package com.osmos.server.payments;

import com.osmos.server.orders.OrderRepo;
import com.osmos.server.orders.entities.Order;
import com.osmos.server.orders.entities.PaymentStatus;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImp implements PaymentService {

    private final OrderRepo orderRepo;

    @Override
    public PaymentIntent intentPayment(long amount, Currency currency) throws StripeException {
        PaymentIntentCreateParams paymentIntentparams = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency(currency.getCurrency())
                .setAutomaticPaymentMethods(PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build())
                .build();
        return PaymentIntent.create(paymentIntentparams);
    }

    @Override
    public void confirmPayment(String clientSecret) {
        Order order = orderRepo.findByClientSecret(clientSecret).orElseThrow();
        order.setPaymentStatus(PaymentStatus.CONFIRMED);
    }

    @Override
    public void failedPayment() {

    }
}
