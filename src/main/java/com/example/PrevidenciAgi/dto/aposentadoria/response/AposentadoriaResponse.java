package com.example.PrevidenciAgi.dto.aposentadoria.response;

import com.example.PrevidenciAgi.Enum.TipoAposentadoria;

public record AposentadoriaResponse(
        Long idAposentadoria,
        TipoAposentadoria tipoAposentadoria,
        String dataAposentar,
        String dataContratada
) {
}
