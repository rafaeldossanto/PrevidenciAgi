package com.example.PrevidenciAgi.dto.deposito.request;

import java.math.BigDecimal;

public record DepositosRequest(
        Long id_aposentadoria,
        String tipo,
        Double valor
) {
}
