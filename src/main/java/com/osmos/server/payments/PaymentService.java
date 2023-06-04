package com.osmos.server.payments;


import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface PaymentService {

    public PaymentIntent intentPayment(long amount, Currency currency) throws StripeException;

    public void confirmPayment(String clientSecret);

    public void failedPayment();

}
