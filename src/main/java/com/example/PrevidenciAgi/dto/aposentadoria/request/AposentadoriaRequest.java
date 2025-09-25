package com.example.PrevidenciAgi.dto.aposentadoria.request;

import jakarta.validation.constraints.NotNull;

public record AposentadoriaRequest(
        @NotNull(message = "ID do cliente é obrigatório")
        Long clienteId,
        @NotNull(message = "Tipo de aposentadoria é obrigatório")
        String tipoAposentadoria,
        @NotNull(message = "Data para se aposentar é obrigatória")
        String dataAposentar,
        @NotNull(message = "Data de contratação é obrigatória")
        String dataContratada,
        @NotNull(message = "Valor do depósito é obrigatório")
        Double valorDeposito
) {
}
