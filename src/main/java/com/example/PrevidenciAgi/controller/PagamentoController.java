package com.example.PrevidenciAgi.controller;

import com.example.PrevidenciAgi.dto.payment.request.PaymentRequest;
import com.example.PrevidenciAgi.dto.payment.response.PaymentResponse;
import com.example.PrevidenciAgi.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/previdencia/pagamento")
public class PagamentoController {
    @Autowired
    private PagamentoService stripeService;

    @PostMapping("/create")
    public ResponseEntity<
            PaymentResponse> createPayment(@RequestBody PaymentRequest request) {
        PaymentResponse response = stripeService.criacaoIntencaoPagamento(request);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<PaymentResponse> confirmPayment(@PathVariable String id) {
        PaymentResponse response = stripeService.confirmacaoPagamento(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<PaymentResponse> getStatus(@PathVariable String id) {
        PaymentResponse response = stripeService.statusPagamento(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<PaymentResponse> cancelPayment(@PathVariable String id) {
        PaymentResponse response = stripeService.cancelamentoIntencaoPagamento(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
    }
}
