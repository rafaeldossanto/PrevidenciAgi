package com.example.PrevidenciAgi.model.simulacao.request;

import com.example.PrevidenciAgi.Enum.Genero;
import com.example.PrevidenciAgi.Enum.TempoRecebendo;
import com.example.PrevidenciAgi.Enum.TipoSimulacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SimulacaoRequest(
        Integer idade,
        Integer taxaJuros,
        TipoSimulacao tipoSimulacao,
        BigDecimal valorMensal,
        Genero genero,
        LocalDate dataAposentar,
        TempoRecebendo tempoRecebimento
        ) {
}
