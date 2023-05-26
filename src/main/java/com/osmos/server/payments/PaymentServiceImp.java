package com.osmos.server.payments;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImp implements PaymentService {

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
    public void confirmPayment() {

    }

    @Override
    public void failedPayment() {

    }
}
