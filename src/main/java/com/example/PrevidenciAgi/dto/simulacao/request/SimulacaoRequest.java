package com.example.PrevidenciAgi.dto.simulacao.request;

import com.example.PrevidenciAgi.enums.Genero;

import java.time.LocalDate;

public record SimulacaoRequest(
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
