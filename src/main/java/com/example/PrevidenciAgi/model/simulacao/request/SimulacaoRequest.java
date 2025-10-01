package com.example.PrevidenciAgi.model.simulacao.request;

import com.example.PrevidenciAgi.enums.Genero;
import com.example.PrevidenciAgi.enums.TipoAposentadoria;

import java.time.LocalDate;

public record SimulacaoRequest(
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
