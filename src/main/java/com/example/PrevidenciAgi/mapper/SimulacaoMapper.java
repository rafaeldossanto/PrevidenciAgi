package com.example.PrevidenciAgi.mapper;

import com.example.PrevidenciAgi.dto.simulacao.response.SimulacaoResponse;
import com.example.PrevidenciAgi.entity.Simulacao;
// Adicione o import da sua classe de formatação
import com.example.PrevidenciAgi.util.TempoFormatter;

public class SimulacaoMapper {

    public static SimulacaoResponse toResponse(Simulacao s) {
        if (s == null) return null;

        // 1. Converter Integer (meses) da Entity para String Formatada para o Response
        String tempoContribuicaoFormatado = null;
        if (s.getTempoContribuicaoEmMeses() != null) {
            tempoContribuicaoFormatado = TempoFormatter.formatarMeses(s.getTempoContribuicaoEmMeses());
        }

        String tempoRecebimentoFormatado = null;
        if (s.getTempoRecebimentoEmMeses() != null) {
            tempoRecebimentoFormatado = TempoFormatter.formatarMeses(s.getTempoRecebimentoEmMeses());
        }

        // 2. Mapeamento para o SimulacaoResponse
        return new SimulacaoResponse(
                s.getIdSimulacao(),
                s.getValorMensal() != null ? s.getValorMensal().doubleValue() : null,
                s.getValorRecebendo() != null ? s.getValorRecebendo().doubleValue() : null,
                s.getGenero() != null ? s.getGenero().name() : null,
                s.getTipoSimulacao() != null ? s.getTipoSimulacao().name() : null,
                s.getDataInicial(),
                s.getDataAposentar(),
                tempoContribuicaoFormatado, // Usando o valor formatado
                tempoRecebimentoFormatado   // Usando o valor formatado
        );
    }
}