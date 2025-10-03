package com.example.PrevidenciAgi.dto.simulacao.request;

import com.example.PrevidenciAgi.entity.Enum.Genero;
import com.example.PrevidenciAgi.entity.Enum.TempoRecebendo;
import com.example.PrevidenciAgi.entity.Enum.TipoSimulacao;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record SimulacaoRequest(
        @NotNull(message = "idade é obrigatória") Integer idade,
        @NotNull(message = "taxaJuros é obrigatória") Integer taxaJuros,
        @NotNull(message = "tipoSimulacao é obrigatório") TipoSimulacao tipoSimulacao,
        // usado apenas para DEPOSITAR
        BigDecimal valorMensal,
        // usado apenas para RECEBER (renda desejada)
        BigDecimal valorDesejado,
        @NotNull(message = "genero é obrigatório") Genero genero,
        @NotNull(message = "dataInicial é obrigatória") LocalDate dataInicial,
        @NotNull(message = "dataAposentar é obrigatória") @Future(message = "dataAposentar deve ser futura") LocalDate dataAposentar,
        String tempoRecebimento,
        String tempoContribuicao
) {}
