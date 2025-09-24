package com.example.PrevidenciAgi.dto.aposentadoria.request;

import com.example.PrevidenciAgi.entity.Enum.TipoAposentadoriaEnum;

import java.time.LocalDateTime;

public record AposentadoriaRequest(
        TipoAposentadoriaEnum tipoAposentadoria,
        Double valor_mensal,
        LocalDateTime data_aposentar,
        LocalDateTime data_inicio,
        Long id
) {
}
