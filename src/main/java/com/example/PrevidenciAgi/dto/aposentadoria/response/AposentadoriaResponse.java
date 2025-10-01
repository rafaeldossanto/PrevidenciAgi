package com.example.PrevidenciAgi.dto.aposentadoria.response;

public record AposentadoriaResponse(
        Long idAposentadoria,
        String tipoAposentadoria,
        String dataAposentar,
        String dataContratada
) {
}
