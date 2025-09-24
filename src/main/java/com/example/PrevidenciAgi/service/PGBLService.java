package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.dto.simulacao.request.SimulacaoRequest;

import java.time.temporal.ChronoUnit;

public class PGBLService {

    private final TributacaoService tributacao;

    // Constantes importantes para o cálculo
    private static final double LIMITE_DEDUCAO = 0.12; // 12% da renda bruta anual
    private static final double TAXA_RENDIMENTO_PADRAO = 0.05; // exemplo: 5% ao ano

    public PGBLService(TributacaoService tributacao) {
        this.tributacao = tributacao;
    }

    /**
     * Calcula o valor líquido no momento do resgate
     */
    public double calcularResgateLiquido(SimulacaoRequest request) {
        double saldoBruto = calcularSaldoProjetado(request);
        long anosContribuicao = ChronoUnit.YEARS.between(
                request.dataInicial(),
                request.dataAposentar()
        );
        double imposto = tributacao.calcularImposto(saldoBruto, anosContribuicao);
        return saldoBruto - imposto;
    }

    /**
     * Calcula a renda bruta anual a partir do valor mensal
     */
    public double calcularRendaAnual(SimulacaoRequest request) {
        return request.valorMensal() * 12;
    }

    /**
     * Calcula o valor máximo dedutível no IR (12% da renda anual)
     */
    public double calcularDeducaoMaxima(SimulacaoRequest request) {
        double rendaAnual = calcularRendaAnual(request);
        return rendaAnual * LIMITE_DEDUCAO;
    }

    /**
     * Calcula a economia de IR no ano com base na alíquota efetiva informada
     */
    public double calcularEconomiaIR(SimulacaoRequest request, double aliquotaIR) {
        double deducao = calcularDeducaoMaxima(request);
        return deducao * aliquotaIR;
    }

    /**
     * Projeta o saldo acumulado até a data de aposentadoria
     */
    public double calcularSaldoProjetado(SimulacaoRequest request) {
        long anosDeContribuicao = ChronoUnit.YEARS.between(
                request.dataInicial(),
                request.dataAposentar()
        );

        // Considera o aporte mensal (não apenas a dedução fiscal)
        double aporteAnual = request.valorMensal() * 12;
        double saldo = 0.0;

        for (int i = 0; i < anosDeContribuicao; i++) {
            saldo = (saldo + aporteAnual) * (1 + TAXA_RENDIMENTO_PADRAO);
        }
        return saldo;
    }
}