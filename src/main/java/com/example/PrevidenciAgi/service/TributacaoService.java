package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.enums.TipoTributacao;
import org.springframework.stereotype.Service;

@Service
public class TributacaoService {

    public double calcularImposto(double saldoBruto, long anosContribuicao, TipoTributacao tipo) {
        return switch (tipo) {
            case PROGRESSIVA -> calcularProgressiva(saldoBruto);
            case REGRESSIVA -> calcularRegressiva(saldoBruto, anosContribuicao);
        };
    }

    private double calcularProgressiva(double saldoBruto) {
        // Exemplo: IR progressivo com faixas fictícias
        if (saldoBruto <= 22847.76) return 0;
        if (saldoBruto <= 33919.80) return saldoBruto * 0.075;
        if (saldoBruto <= 45012.60) return saldoBruto * 0.15;
        if (saldoBruto <= 55976.16) return saldoBruto * 0.225;
        return saldoBruto * 0.275;
    }

    private double calcularRegressiva(double saldoBruto, long anosContribuicao) {
        // Exemplo de tabela regressiva
        if (anosContribuicao >= 10) return saldoBruto * 0.10;
        if (anosContribuicao >= 8) return saldoBruto * 0.15;
        if (anosContribuicao >= 6) return saldoBruto * 0.20;
        if (anosContribuicao >= 4) return saldoBruto * 0.25;
        if (anosContribuicao >= 2) return saldoBruto * 0.30;
        return saldoBruto * 0.35;
    }
}