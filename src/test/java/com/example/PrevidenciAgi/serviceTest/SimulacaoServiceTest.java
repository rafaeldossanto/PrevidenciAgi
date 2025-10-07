package com.example.PrevidenciAgi.serviceTest;


import com.example.PrevidenciAgi.model.simulacao.request.SimulacaoRequest;
import com.example.PrevidenciAgi.Enum.Genero;
import com.example.PrevidenciAgi.Enum.TempoRecebendo;
import com.example.PrevidenciAgi.Enum.TipoSimulacao;
import com.example.PrevidenciAgi.model.simulacao.Simulacao;
import com.example.PrevidenciAgi.repository.SimulacaoRepository;
import com.example.PrevidenciAgi.service.SimulacaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SimulacaoServiceTest {

    @Mock
    private SimulacaoRepository simulacaoRepository;

    @InjectMocks
    private SimulacaoService simulacaoService;

    @Test
    void SimulacaoDeveSalvar_osDadosColocadosNoRequest_EFazerOcalculoCorreto(){
        Integer idade = 20;
        Integer taxaJuros = 5;
        TipoSimulacao tipoSimulacao = TipoSimulacao.DEPOSITAR;
        BigDecimal valorMensal = BigDecimal.valueOf(1000);
        Genero genero = Genero.MASCULINO;
        LocalDate dataAposentar = LocalDate.of(2050, 6, 20);
        TempoRecebendo tempoRecebendo = TempoRecebendo.ANOS_5;

        SimulacaoRequest simulacao = new SimulacaoRequest(
                idade,
                taxaJuros,
                tipoSimulacao,
                valorMensal,
                genero,
                dataAposentar,
                tempoRecebendo
        );

        simulacaoService.calcularSimulacao(simulacao);

        ArgumentCaptor<Simulacao> captor = ArgumentCaptor.forClass(Simulacao.class);
        verify(simulacaoRepository).save(captor.capture());
        Simulacao simulacaoSalva = captor.getValue();

        assertEquals(idade, simulacaoSalva.getIdade());
        assertEquals(valorMensal, simulacaoSalva.getValorMensal());
        assertNotNull(simulacaoSalva.getValorRecebendo());
        assertNotNull(simulacaoSalva.getTotalInvestidoJuros());

    }
}
