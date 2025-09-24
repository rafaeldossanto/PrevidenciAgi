package com.example.PrevidenciAgi.service;

public class TributacaoProgressivaService implements TributacaoService {

    @Override
    public double calcularImposto(double saldoBruto, long anosContribuicao) {
        // Aqui a alíquota é a tabela normal do IRPF
        // Simplificando só como exemplo
        if (saldoBruto <= 22847.76) return 0;
        else if (saldoBruto <= 33919.80) return saldoBruto * 0.075;
        else if (saldoBruto <= 45012.60) return saldoBruto * 0.15;
        else if (saldoBruto <= 55976.16) return saldoBruto * 0.225;
        else return saldoBruto * 0.275;
    }
}
