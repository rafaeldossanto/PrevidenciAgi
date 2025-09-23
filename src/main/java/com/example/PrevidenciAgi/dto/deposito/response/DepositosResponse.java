package com.example.PrevidenciAgi.dto.deposito.response;

import com.example.PrevidenciAgi.entity.Depositos;

import java.time.LocalDateTime;

public record DepositosResponse(
        Long idDeposito,
        String tipo,
        Double valor,
        LocalDateTime dataDeposito
) {
    public DepositosResponse(Depositos deposito) {
        this(
                deposito.getIdDeposito(),
                deposito.getTipo(),
                deposito.getValor(),
                deposito.getDataDeposito()

        );
    }
}
