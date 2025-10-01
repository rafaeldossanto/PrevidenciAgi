package com.example.PrevidenciAgi.service;


import com.example.PrevidenciAgi.dto.simulacao.request.SimulacaoRequest;
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
        }

        BigDecimal montanteFinal = calculandoMontanteFinal(request);
        BigDecimal totalInvestido = request.valorMensal().multiply(BigDecimal.valueOf((request.dataAposentar().getYear() - LocalDate.now().getYear())* 12L));

        simulacao.setIdade(request.idade());
        simulacao.setValorMensal(request.valorMensal());
        simulacao.setTaxaJuros(request.taxaJuros());
        simulacao.setTipoSimulacao(request.tipoSimulacao());
        simulacao.setGenero(request.genero());
        simulacao.setDataAposentar(request.dataAposentar());
        simulacao.setTempoRecebimento(request.tempoRecebimento());
        simulacao.setValorInvestido(totalInvestido);
        simulacao.setTotalInvestidoJuros(montanteFinal);

        simulacaoRepository.save(simulacao);
    }

    private BigDecimal calculandoMontanteFinal(SimulacaoRequest request){
        double taxaMensal = (double) request.taxaJuros() /12/100.0;
        int mesesContribuidos = (request.dataAposentar().getYear() - LocalDate.now().getYear()) *12;

        return request.valorMensal().multiply(
                BigDecimal.valueOf((Math.pow(1 + taxaMensal, mesesContribuidos) - 1) / taxaMensal)
        );
    }
}
