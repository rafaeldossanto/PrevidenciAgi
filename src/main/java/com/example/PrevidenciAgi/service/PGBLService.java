package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.dto.simulacao.request.SimulacaoRequest;
import org.springframework.stereotype.Service; // Import necessário
import java.time.temporal.ChronoUnit;

@Service // Adicionar para ser injetado
public class PGBLService {

    // Injeta as duas implementações
    private final TributacaoService tributacaoProgressiva;
    private final TributacaoService tributacaoRegressiva;

    // Constantes importantes para o cálculo
    private static final double LIMITE_DEDUCAO = 0.12;
    private static final double TAXA_RENDIMENTO_PADRAO = 0.05;

    // Construtor CORRIGIDO: injeta as implementações específicas
    public PGBLService(TributacaoProgressivaService tributacaoProgressiva, TributacaoRegressivaService tributacaoRegressiva) {
        this.tributacaoProgressiva = tributacaoProgressiva;
        this.tributacaoRegressiva = tributacaoRegressiva;
    }

    /**
     * Calcula o valor líquido no momento do resgate, com seleção de tributação.
     * Assinatura alterada para receber o regimeTributario.
     */
    public double calcularResgateLiquido(SimulacaoRequest request, String regimeTributario) {
        // 1. Seleciona o Serviço de Tributação correto
        TributacaoService tributacao;
        if ("PROGRESSIVA".equalsIgnoreCase(regimeTributario)) {
            tributacao = tributacaoProgressiva;
        } else if ("REGRESSIVA".equalsIgnoreCase(regimeTributario)) {
            tributacao = tributacaoRegressiva;
        } else {
            throw new IllegalArgumentException("Regime de tributação inválido: " + regimeTributario);
        }

        // 2. Continua o cálculo PGBL (baseado no saldo bruto)
        double saldoBruto = calcularSaldoProjetado(request);
        long anosContribuicao = ChronoUnit.YEARS.between(
                request.dataInicial(),
                request.dataAposentar()
        );
        double imposto = tributacao.calcularImposto(saldoBruto, anosContribuicao);
        return saldoBruto - imposto;
    }

    // ... (Mantenha os outros métodos auxiliares: calcularRendaAnual, calcularDeducaoMaxima, etc.)
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
    public double calcularRendaAnual(SimulacaoRequest request) {
        return request.valorMensal() * 12;
    }
    public double calcularDeducaoMaxima(SimulacaoRequest request) {
        double rendaAnual = calcularRendaAnual(request);
        return rendaAnual * LIMITE_DEDUCAO;
    }
    public double calcularEconomiaIR(SimulacaoRequest request, double aliquotaIR) {
        double deducao = calcularDeducaoMaxima(request);
        return deducao * aliquotaIR;
    }
}