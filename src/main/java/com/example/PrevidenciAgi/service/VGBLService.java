package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.dto.simulacao.request.SimulacaoRequest;
import org.springframework.stereotype.Service; // Import necessário
import java.time.temporal.ChronoUnit;

@Service // Adicionar para ser injetado
public class VGBLService {

    // Injeta as duas implementações
    private final TributacaoService tributacaoProgressiva;
    private final TributacaoService tributacaoRegressiva;

    private static final double TAXA_RENDIMENTO_PADRAO = 0.05;

    // Construtor CORRIGIDO: injeta as implementações específicas
    public VGBLService(TributacaoProgressivaService tributacaoProgressiva, TributacaoRegressivaService tributacaoRegressiva) {
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

        // 2. Continua o cálculo VGBL (imposto só sobre rendimentos)
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

    // ... (Mantenha os outros métodos auxiliares: calcularTotalAportado, calcularSaldoProjetado)
    public double calcularTotalAportado(SimulacaoRequest request) {
        long anosContribuicao = ChronoUnit.YEARS.between(
                request.dataInicial(),
                request.dataAposentar()
        );
        return request.valorMensal() * 12 * anosContribuicao;
    }

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