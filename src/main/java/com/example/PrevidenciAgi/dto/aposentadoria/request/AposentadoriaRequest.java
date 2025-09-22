package com.example.PrevidenciAgi.dto.aposentadoria.request;

public record AposentadoriaRequest(
        String tipoAposentadoria,
        String dataAposentar,
        String dataContratada
) {
}
