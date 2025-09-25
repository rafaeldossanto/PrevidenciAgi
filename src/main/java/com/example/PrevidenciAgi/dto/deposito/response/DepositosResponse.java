package com.example.PrevidenciAgi.dto.deposito.response;

import com.example.PrevidenciAgi.entity.Depositos;
import com.example.PrevidenciAgi.enums.TiposDepositos;

import java.time.LocalDateTime;

public record DepositosResponse(
        Long idDeposito,
        TiposDepositos tipo,
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

    public static DepositosResponse fromEntity(Depositos deposito) {
        return new DepositosResponse(
                deposito.getIdDeposito(),
                deposito.getTipo(),
                deposito.getValor(),
                deposito.getDataDeposito()
        );
    }
}
