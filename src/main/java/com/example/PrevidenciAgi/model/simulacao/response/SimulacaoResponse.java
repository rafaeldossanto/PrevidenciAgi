package com.example.PrevidenciAgi.model.simulacao.response;

import com.example.PrevidenciAgi.enums.Genero;
import com.example.PrevidenciAgi.enums.TipoAposentadoria;

import java.time.LocalDate;

public record SimulacaoResponse(
        Long idSimulacao,
        Double valorMensal,
        Double valorReceber,
        Genero genero,
        TipoAposentadoria tipoContribuicao,
        LocalDate dataInicial,
        LocalDate dataAposentar,
        Integer tempoContribuicao,
        Integer tempoRecebimento
) {
}
