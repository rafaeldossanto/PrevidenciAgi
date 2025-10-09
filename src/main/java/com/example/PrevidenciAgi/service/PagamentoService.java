package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.dto.payment.request.PaymentRequest;
import com.example.PrevidenciAgi.dto.payment.response.PaymentResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PagamentoService {

    @Value("${stripe.secret.key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public PaymentResponse criacaoIntencaoPagamento(PaymentRequest request) {
        try {
            Long amountInCents = request.getAmount()
                    .multiply(BigDecimal.valueOf(100))
                    .longValue();

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amountInCents)
                    .setCurrency("brl")
                    .addPaymentMethodType("pix")
                    .putMetadata("description", request.getDescription())
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            return buildSuccessResponse(paymentIntent);
        } catch (StripeException e) {
            return contrucaoErro("Erro ao criar pagamento: " + e.getMessage());
        }
    }

    public PaymentResponse confirmacaoPagamento(String paymentIntentId) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            PaymentIntent confirmedIntent = paymentIntent.confirm();

            return buildSuccessResponse(confirmedIntent);
        } catch (StripeException e) {
            return contrucaoErro("Erro ao confirmar pagamento: " + e.getMessage());
        }
    }

    public PaymentResponse statusPagamento(String paymentIntentId) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            return buildSuccessResponse(paymentIntent);
        } catch (StripeException e) {
            return contrucaoErro("Erro ao consultar status: " + e.getMessage());
        }
    }

    public PaymentResponse cancelamentoIntencaoPagamento(String paymentIntentId) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            PaymentIntent canceledIntent = paymentIntent.cancel();
            return buildSuccessResponse(canceledIntent);
        } catch (StripeException e) {
            return contrucaoErro("Erro ao cancelar pagamento: " + e.getMessage());
        }
    }

    private PaymentResponse buildSuccessResponse(PaymentIntent paymentIntent) {
        PaymentResponse response = new PaymentResponse();
        response.setClientSecret(paymentIntent.getClientSecret());
        response.setPaymentIntentId(paymentIntent.getId());
        response.setStatus(paymentIntent.getStatus());
        response.setSuccess(true);
        response.setMessage("Operação realizada com sucesso");
        return response;
    }

    private PaymentResponse contrucaoErro(String message) {
        PaymentResponse response = new PaymentResponse();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }
}
