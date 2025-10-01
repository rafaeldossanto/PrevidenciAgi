package com.example.PrevidenciAgi.model.deposito.response;

import com.example.PrevidenciAgi.enums.TipoDeposito;
import com.example.PrevidenciAgi.model.deposito.Depositos;

import java.time.LocalDateTime;

public record DepositosResponse(
        Long idDeposito,
        TipoDeposito tipo,
        Double valor,
        LocalDateTime dataDeposito
) {
    public DepositosResponse(Depositos depositos) {
        this(
                depositos.getIdDeposito(),
                depositos.getTipo(),
                depositos.getValor(),
                depositos.getDataDeposito()
        );
    }
}
