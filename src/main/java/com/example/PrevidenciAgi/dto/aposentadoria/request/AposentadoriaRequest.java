package com.example.PrevidenciAgi.dto.aposentadoria.request;

import com.example.PrevidenciAgi.entity.Enum.TipoAposentadoriaEnum;

public record AposentadoriaRequest(
        TipoAposentadoriaEnum tipoAposentadoria,
        String dataAposentar,
        String dataContratada
) {
}
