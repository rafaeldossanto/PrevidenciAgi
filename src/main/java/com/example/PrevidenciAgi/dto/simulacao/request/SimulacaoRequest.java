package com.example.PrevidenciAgi.dto.simulacao.request;

import com.example.PrevidenciAgi.entity.Enum.Genero;
import com.example.PrevidenciAgi.entity.Enum.TempoRecebendo;
import com.example.PrevidenciAgi.entity.Enum.TipoSimulacao;

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
