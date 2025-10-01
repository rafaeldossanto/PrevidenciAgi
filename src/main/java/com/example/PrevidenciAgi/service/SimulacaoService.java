package com.example.PrevidenciAgi.service;


import com.example.PrevidenciAgi.dto.simulacao.request.SimulacaoRequest;
import com.example.PrevidenciAgi.entity.Enum.TempoRecebendo;
import com.example.PrevidenciAgi.entity.Enum.TipoSimulacao;
import com.example.PrevidenciAgi.entity.Simulacao;
import com.example.PrevidenciAgi.repository.SimulacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class SimulacaoService {

    @Autowired
    private SimulacaoRepository simulacaoRepository;


    public void calcularSimulacaoDepositando(SimulacaoRequest request) {
        Simulacao simulacao = new Simulacao();

        if (request.tipoSimulacao().equals(TipoSimulacao.DEPOSITAR)
                && request.valorMensal() == null) {
            throw new IllegalArgumentException("Valor mensal deve ser fornecido para simulação do tipo DEPOSITAR.");
        } else if (request.tipoSimulacao().equals(TipoSimulacao.DEPOSITAR)) {
            BigDecimal montanteFinal = calculandoMontanteFinal(request);
            BigDecimal valorRecebendo = montanteFinal.divide(BigDecimal.valueOf(request.tempoRecebimento().toAnos() * 12F));

            simulacao.setValorRecebendo(valorRecebendo);
            simulacao.setTotalInvestidoJuros(montanteFinal);
        }

        if (request.tipoSimulacao().equals(TipoSimulacao.RECEBER)
        && request.valorMensal() != null) {
            throw new IllegalArgumentException("Valor mensal nessa ocasiao deve ser vazio.");
        } else if (request.tipoSimulacao().equals(TipoSimulacao.RECEBER)){
            BigDecimal valorRecebendo = calcularSimulacaoRecebendo(request);

            simulacao.setValorRecebendo(valorRecebendo);
        }
        BigDecimal totalInvestido = request.valorMensal().multiply(BigDecimal.valueOf((request.dataAposentar().getYear() - LocalDate.now().getYear()) * 12L));


        simulacao.setIdade(request.idade());
        simulacao.setValorMensal(request.valorMensal());
        simulacao.setTaxaJuros(request.taxaJuros());
        simulacao.setTipoSimulacao(request.tipoSimulacao());
        simulacao.setGenero(request.genero());
        simulacao.setDataAposentar(request.dataAposentar());
        simulacao.setTempoRecebimento(request.tempoRecebimento());
        simulacao.setValorInvestido(totalInvestido);


        simulacaoRepository.save(simulacao);
    }

    private BigDecimal calculandoMontanteFinal(SimulacaoRequest request) {
        double taxaMensal = (double) request.taxaJuros() / 12 / 100.0;
        int mesesContribuidos = (request.dataAposentar().getYear() - LocalDate.now().getYear()) * 12;

        return request.valorMensal().multiply(
                BigDecimal.valueOf((Math.pow(1 + taxaMensal, mesesContribuidos) - 1) / taxaMensal)
        );
    }

    private BigDecimal calcularSimulacaoRecebendo(SimulacaoRequest request) {
        BigDecimal rendaMensalDesejada = request.valorMensal();
        int mesesRecebimento = request.tempoRecebimento().toAnos() * 12;
        double taxaJurosMensal = (double) request.taxaJuros() / 12 / 100.0;
        int mesesAteAposentadoria = (request.dataAposentar().getYear() - LocalDate.now().getYear()) * 12;

        double vp = rendaMensalDesejada.doubleValue() * (1 - Math.pow(1 + taxaJurosMensal, -mesesRecebimento)) / taxaJurosMensal;

        double valorMensal = vp * taxaJurosMensal / (Math.pow(1 + taxaJurosMensal, mesesAteAposentadoria) - 1);

        return BigDecimal.valueOf(valorMensal);
    }
}
