package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.dto.simulacao.request.SimulacaoRequest;
import com.example.PrevidenciAgi.entity.Enum.TipoSimulacao;
import com.example.PrevidenciAgi.entity.Simulacao;
import com.example.PrevidenciAgi.repository.SimulacaoRepository;
import com.example.PrevidenciAgi.util.TempoFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;

@Service
public class SimulacaoService {

    private final SimulacaoRepository simulacaoRepository;

    @Autowired
    public SimulacaoService(SimulacaoRepository simulacaoRepository) {
        this.simulacaoRepository = simulacaoRepository;
    }

    // 1) Calcula e retorna o objeto Simulacao (não salva)
    public Simulacao calcularSimulacao(SimulacaoRequest request) {
        validarRequestPorTipo(request);

        Simulacao simulacao = new Simulacao();
        long mesesContribuidos = ChronoUnit.MONTHS.between(request.dataInicial(), request.dataAposentar());
        if (mesesContribuidos < 1) throw new IllegalArgumentException("Período até aposentadoria precisa ser maior que 0 meses.");

        // Precisamos extrair o valor em meses a partir da String
        int mesesRecebimento = TempoFormatter.converterParaMeses(request.tempoRecebimento());

        if (request.tipoSimulacao().equals(TipoSimulacao.DEPOSITAR)) {
            // exige valorMensal
            if (request.valorMensal() == null) throw new IllegalArgumentException("Para DEPOSITAR informe valorMensal.");

            BigDecimal montanteFinal = calcularMontanteFinal(request.valorMensal(), request.taxaJuros(), mesesContribuidos);
            BigDecimal valorRecebendo = montanteFinal.divide(BigDecimal.valueOf(mesesRecebimento), 2, RoundingMode.HALF_UP);

            BigDecimal totalInvestido = request.valorMensal().multiply(BigDecimal.valueOf(mesesContribuidos));

            simulacao.setValorMensal(request.valorMensal());
            simulacao.setTotalInvestidoJuros(montanteFinal);
            simulacao.setValorRecebendo(valorRecebendo);
            simulacao.setValorInvestido(totalInvestido);

        } else { // RECEBER
            if (request.valorDesejado() == null) throw new IllegalArgumentException("Para RECEBER informe valorDesejado (renda mensal desejada).");

            double taxaMensal = (double) request.taxaJuros() / 12 / 100.0;
            int mesesAteAposent = (int) mesesContribuidos;

            // Valor presente necessário no momento da aposentadoria para pagar a renda desejada:
            double vp = request.valorDesejado().doubleValue() * (1 - Math.pow(1 + taxaMensal, -mesesRecebimento)) / taxaMensal;

            // Agora calcula a contribuição mensal necessária para alcançar esse VP no prazo
            double denom = Math.pow(1 + taxaMensal, mesesAteAposent) - 1;
            if (denom <= 0) throw new IllegalArgumentException("Parâmetros de taxa/prazo inválidos.");
            double aporteMensalNecessario = vp * taxaMensal / denom;

            BigDecimal valorMensal = BigDecimal.valueOf(aporteMensalNecessario).setScale(2, RoundingMode.HALF_UP);

            BigDecimal montanteFinal = BigDecimal.valueOf(vp);
            BigDecimal totalInvestido = valorMensal.multiply(BigDecimal.valueOf(mesesContribuidos));

            simulacao.setValorMensal(valorMensal);
            simulacao.setValorRecebendo(request.valorDesejado());
            simulacao.setTotalInvestidoJuros(montanteFinal);
            simulacao.setValorInvestido(totalInvestido);
        }

        // campos comuns
        simulacao.setIdade(request.idade());
        simulacao.setTaxaJuros(request.taxaJuros());
        simulacao.setTipoSimulacao(request.tipoSimulacao());
        simulacao.setGenero(request.genero());

        // armazenamos o valor já formatado
        simulacao.setTempoRecebimento(TempoFormatter.formatar(request.tempoRecebimento()));
        simulacao.setTempoContribuicao(TempoFormatter.formatar(request.tempoContribuicao()));

        return simulacao;
    }

    // 2) Salva um objeto Simulacao no banco
    public Simulacao salvarSimulacao(Simulacao simulacao) {
        return simulacaoRepository.save(simulacao);
    }

    private void validarRequestPorTipo(SimulacaoRequest request) {
        if (request.tipoSimulacao() == null) throw new IllegalArgumentException("tipoSimulacao é obrigatório.");
    }

    private BigDecimal calcularMontanteFinal(BigDecimal aporteMensal, int taxaAnualPercent, long meses) {
        double taxaMensal = (double) taxaAnualPercent / 12 / 100.0;
        if (taxaMensal == 0) {
            return aporteMensal.multiply(BigDecimal.valueOf(meses));
        }
        double fator = (Math.pow(1 + taxaMensal, meses) - 1) / taxaMensal;
        double resultado = aporteMensal.doubleValue() * fator;
        return BigDecimal.valueOf(resultado).setScale(2, RoundingMode.HALF_UP);
    }
}