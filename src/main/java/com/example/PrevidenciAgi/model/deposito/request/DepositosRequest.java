package com.example.PrevidenciAgi.model.deposito.request;

import com.example.PrevidenciAgi.enums.TipoDeposito;

public record DepositosRequest(
        TipoDeposito tipo,
        Double valor
) {
}
