package com.example.PrevidenciAgi.dto.aposentadoria.response;

import com.example.PrevidenciAgi.entity.Enum.TipoAposentadoriaEnum;

public record AposentadoriaResponse(
        Long idAposentadoria,
        TipoAposentadoriaEnum tipoAposentadoria,
        String dataAposentar,
        String dataContratada
) {
}
