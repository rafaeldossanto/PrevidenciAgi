package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.dto.simulacao.request.SimulacaoRequest;
import com.example.PrevidenciAgi.entity.Enum.TipoPlano;
import com.example.PrevidenciAgi.entity.Enum.TipoTributacao;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
public class PrevidenciaService {

    private final TributacaoService tributacaoService;
    private static final double TAXA_RENDIMENTO_PADRAO = 0.05;
    private static final double LIMITE_DEDUCAO = 0.12; // 12% renda anual

    public PrevidenciaService(TributacaoService tributacaoService) {
        this.tributacaoService = tributacaoService;
    }

    public double calcularResgateLiquido(SimulacaoRequest request, TipoPlano plano, TipoTributacao tributacao) {
        double saldoBruto = calcularSaldoProjetado(request, plano);
        long anosContribuicao = ChronoUnit.YEARS.between(request.dataInicial(), request.dataAposentar());
        double imposto = tributacaoService.calcularImposto(saldoBruto, anosContribuicao, tributacao);
        return saldoBruto - imposto;
    }

    public double calcularRendaAnual(SimulacaoRequest request) {
        return request.valorMensal() * 12;
    }

    public double calcularDeducaoMaxima(SimulacaoRequest request) {
        return calcularRendaAnual(request) * LIMITE_DEDUCAO;
    }

    public double calcularEconomiaIR(SimulacaoRequest request, double aliquotaIR) {
        double deducao = calcularDeducaoMaxima(request);
        return deducao * aliquotaIR;
    }

    public double calcularSaldoProjetado(SimulacaoRequest request, TipoPlano plano) {
        long anos = ChronoUnit.YEARS.between(request.dataInicial(), request.dataAposentar());
        double aporteAnual = (plano == TipoPlano.PGBL)
                ? calcularDeducaoMaxima(request)
                : calcularRendaAnual(request);

        double saldo = 0.0;
        for (int i = 0; i < anos; i++) {
            saldo = (saldo + aporteAnual) * (1 + TAXA_RENDIMENTO_PADRAO);
        }
        return saldo;
    }
}