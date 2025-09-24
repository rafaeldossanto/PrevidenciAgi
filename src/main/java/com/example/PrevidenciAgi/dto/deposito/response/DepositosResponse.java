package com.example.PrevidenciAgi.dto.deposito.response;

import com.example.PrevidenciAgi.enums.TiposDepositos;

import java.time.LocalDateTime;

public record DepositosResponse(
        Long idDeposito,
        TiposDepositos tipo,
        Double valor,
        LocalDateTime dataDeposito
) {
}
