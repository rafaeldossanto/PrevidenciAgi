package com.example.PrevidenciAgi.service;

public class TributacaoRegressivaService implements TributacaoService {

    @Override
    public double calcularImposto(double saldoBruto, long anosContribuicao) {
        double aliquota;

        if (anosContribuicao <= 2) aliquota = 0.35;
        else if (anosContribuicao <= 4) aliquota = 0.30;
        else if (anosContribuicao <= 6) aliquota = 0.25;
        else if (anosContribuicao <= 8) aliquota = 0.20;
        else if (anosContribuicao <= 10) aliquota = 0.15;
        else aliquota = 0.10;

        return saldoBruto * aliquota;
    }
}

