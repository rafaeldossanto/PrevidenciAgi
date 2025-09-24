package com.example.PrevidenciAgi.dto.contrato.request;// package com.example.PrevidenciAgi.dto.contrato.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ContratoRequest(

        // Identifica qual simulação o cliente quer transformar em contrato
        @NotNull(message = "O ID da simulação aprovada é obrigatório.")
        @Positive(message = "O ID da simulação deve ser um número válido.")
        Long idSimulacaoAprovada,

        // ID do cliente, que deve ser obtido do token de segurança, mas mantido aqui para validação
        @NotNull(message = "O ID do cliente é obrigatório.")
        @Positive(message = "O ID do cliente deve ser um número válido.")
        Long idCliente
) {
}