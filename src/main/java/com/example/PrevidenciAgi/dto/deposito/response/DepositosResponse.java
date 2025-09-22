package com.example.PrevidenciAgi.dto.deposito.response;

import java.time.LocalDateTime;

public record DepositosResponse(
        Long idDeposito,
        String tipo,
        Double valor,
        LocalDateTime dataDeposito
) {
}
