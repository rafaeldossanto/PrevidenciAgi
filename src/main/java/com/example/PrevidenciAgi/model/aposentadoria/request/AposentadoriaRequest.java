package com.example.PrevidenciAgi.model.aposentadoria.request;

public record AposentadoriaRequest(
        String tipoAposentadoria,
        String dataAposentar,
        String dataContratada
) {
}
