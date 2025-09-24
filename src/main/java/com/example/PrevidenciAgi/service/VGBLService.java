package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.dto.simulacao.request.SimulacaoRequest;

import java.time.temporal.ChronoUnit;

public class VGBLService {

    private final TributacaoService tributacao;

    // Constante: exemplo de taxa de rendimento padrão anual
    private static final double TAXA_RENDIMENTO_PADRAO = 0.05;

    public VGBLService(TributacaoService tributacao) {
        this.tributacao = tributacao;
    }

    /**
     * Calcula o valor líquido no momento do resgate
     */
    public double calcularResgateLiquido(SimulacaoRequest request) {
        double saldoBruto = calcularSaldoProjetado(request);
        double totalAportado = calcularTotalAportado(request);

        long anosContribuicao = ChronoUnit.YEARS.between(
                request.dataInicial(),
                request.dataAposentar()
        );

        // No VGBL, imposto incide apenas sobre os rendimentos
        double rendimento = saldoBruto - totalAportado;
        double imposto = tributacao.calcularImposto(rendimento, anosContribuicao);

        return saldoBruto - imposto;
    }

    /**
     * Calcula o total de aportes realizados até a aposentadoria
     */
    public double calcularTotalAportado(SimulacaoRequest request) {
        long anosContribuicao = ChronoUnit.YEARS.between(
                request.dataInicial(),
                request.dataAposentar()
        );
        return request.valorMensal() * 12 * anosContribuicao;
    }

    /**
     * Projeta o saldo acumulado até a data de aposentadoria
     */
    public double calcularSaldoProjetado(SimulacaoRequest request) {
        long anosDeContribuicao = ChronoUnit.YEARS.between(
                request.dataInicial(),
                request.dataAposentar()
        );

        double aporteAnual = request.valorMensal() * 12;
        double saldo = 0.0;

        for (int i = 0; i < anosDeContribuicao; i++) {
            saldo = (saldo + aporteAnual) * (1 + TAXA_RENDIMENTO_PADRAO);
        }
        return saldo;
    }

}