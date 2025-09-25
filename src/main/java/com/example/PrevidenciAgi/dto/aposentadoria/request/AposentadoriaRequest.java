package com.example.PrevidenciAgi.dto.aposentadoria.request;

import com.example.PrevidenciAgi.entity.Enum.TipoAposentadoriaEnum;

import java.time.LocalDate;

public record AposentadoriaRequest(
        TipoAposentadoriaEnum tipoAposentadoria,
        Double valor_mensal,
        Double valor_deposito,
        LocalDate data_contratada,
        LocalDate data_aposentar,
        LocalDate data_inicio,
        Long id
) {
}
