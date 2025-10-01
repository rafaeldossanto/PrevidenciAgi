package com.example.PrevidenciAgi.model.aposentadoria.response;

public record AposentadoriaResponse(
        Long idAposentadoria,
        String tipoAposentadoria,
        String dataAposentar,
        String dataContratada
) {
}
