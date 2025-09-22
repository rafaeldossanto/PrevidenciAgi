package com.example.PrevidenciAgi.dto.simulacao.request;

import java.time.LocalDate;

public record SimulacaoRequest(
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
