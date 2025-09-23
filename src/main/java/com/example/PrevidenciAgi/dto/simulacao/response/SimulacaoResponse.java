package com.example.PrevidenciAgi.dto.simulacao.response;

import com.example.PrevidenciAgi.enums.Genero;

import java.time.LocalDate;

public record SimulacaoResponse(
        Long idSimulacao,
        Double valorMensal,
        Double valorReceber,
        Genero genero,
        String tipoContribuicao,
        LocalDate dataInicial,
        LocalDate dataAposentar,
        Integer tempoContribuicao,
        Integer tempoRecebimento
) {
}
