package com.example.PrevidenciAgi.dto.aposentadoria.request;

import com.example.PrevidenciAgi.enums.TipoAposentadoria;

public record AposentadoriaRequest(
        TipoAposentadoria tipoAposentadoria,
        String dataAposentar,
        String dataContratada
) {
}
