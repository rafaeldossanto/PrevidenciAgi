package com.example.PrevidenciAgi.dto.simulacao.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SimulacaoResponse(
        Long idSimulacao,
        BigDecimal valorMensalReceber,
        BigDecimal valorReceber,
        String genero,
        String tipoContribuicao,
        LocalDate dataInicial,
        LocalDate dataAposentar,
        String tempoContribuicao,
        String tempoRecebimento
) {
}

