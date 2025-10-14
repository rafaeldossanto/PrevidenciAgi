package com.example.PrevidenciAgi.dto.deposito.response;

import com.example.PrevidenciAgi.entity.Enum.TipoDeposito;

import java.time.LocalDateTime;

public record DepositosResponse(
        TipoDeposito tipo,
        Double valor,
        String nomeCliente,
        LocalDateTime dataDeposito
) {
}
