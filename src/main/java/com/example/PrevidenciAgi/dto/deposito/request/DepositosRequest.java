package com.example.PrevidenciAgi.dto.deposito.request;

public record DepositosRequest(
        Long id_aposentadoria,
        String tipo,
        Double valor
) {
}
