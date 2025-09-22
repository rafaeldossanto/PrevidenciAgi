package com.example.PrevidenciAgi.dto.simulacao.response;

import java.time.LocalDate;

public record SimulacaoResponse(
        Long idSimulacao,
        Double valorMensal,
        Double valorReceber,
        String genero,
        String tipoContribuicao,
        LocalDate dataInicial,
        LocalDate dataAposentar,
        Integer tempoContribuicao,
        Integer tempoRecebimento
) {
}
