package com.example.PrevidenciAgi.dto.aposentadoria.response;

import com.example.PrevidenciAgi.entity.Aposentadoria;
import com.example.PrevidenciAgi.enums.TipoAposentadoria;

public record AposentadoriaResponse(
        Long idAposentadoria,
        TipoAposentadoria tipoAposentadoria,
        String dataAposentar,
        String dataContratada
) {
    public AposentadoriaResponse(Aposentadoria aposentadoria) {
        this(
                aposentadoria.getIdAposentadoria(),
                aposentadoria.getTipoAposentadoria(),
                aposentadoria.getDataAposentar(),
                aposentadoria.getDataContratada()
        );
    }
}
