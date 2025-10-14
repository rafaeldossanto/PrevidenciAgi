package com.example.PrevidenciAgi.dto.aposentadoria.response;

import com.example.PrevidenciAgi.entity.Enum.TipoAposentadoria;

import java.time.LocalDate;

public record AposentadoriaResponse(
        TipoAposentadoria tipoAposentadoria,
        LocalDate dataAposentar,
        LocalDate dataInicio,
        Double valorMensal,
        Double valorDeposito,
        String nomeCliente,
        LocalDate dataContratada
        ) {
}
