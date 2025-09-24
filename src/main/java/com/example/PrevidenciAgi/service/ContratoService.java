package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.dto.contrato.request.ContratoRequest;
import com.example.PrevidenciAgi.dto.contrato.response.ContratoResponse;
import com.example.PrevidenciAgi.entity.ContratoPrevidencia;
import com.example.PrevidenciAgi.entity.Simulacao;
import com.example.PrevidenciAgi.repository.ContratoRepository;
import com.example.PrevidenciAgi.repository.SimulacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
public class ContratoService {

    private final SimulacaoRepository simulacaoRepository;
    private final ContratoRepository contratoRepository;

    public ContratoService(SimulacaoRepository simulacaoRepository, ContratoRepository contratoRepository) {
        this.simulacaoRepository = simulacaoRepository;
        this.contratoRepository = contratoRepository;
    }

    @Transactional
    public ContratoResponse assinarContrato(ContratoRequest request) {

        // 1. Buscar e Validar a Simulação
        Simulacao simulacao = simulacaoRepository.findById(request.idSimulacaoAprovada())
                .orElseThrow(() -> new EntityNotFoundException("Simulação não encontrada."));

        // 2. Mapear Simulação para Contrato
        ContratoPrevidencia novoContrato = new ContratoPrevidencia();

        // Mapeamento dos dados do plano
        novoContrato.setCliente(simulacao.getCliente());
        novoContrato.setValorMensal(simulacao.getValorMensal());
        novoContrato.setTipoPlano(simulacao.getTipoContribuicao());
        // Assumindo que a Simulação tem o campo regimeTributario
        // novoContrato.setRegimeTributario(simulacao.getRegimeTributario());

        // Mapeamento dos dados de Aposentadoria (Projeção)
        novoContrato.setDataAposentadoria(simulacao.getDataAposentar());
        novoContrato.setValorBeneficioEsperado(simulacao.getValorReceber());

        // Dados do Contrato
        novoContrato.setDataInicioContrato(LocalDate.now()); // Data da assinatura
        novoContrato.setStatus("ASSINADO");

        // 3. Salvar o Contrato
        ContratoPrevidencia contratoSalvo = contratoRepository.save(novoContrato);

        // 4. (Opcional) Remover o rascunho da Simulação ou marcar como CONVERTIDA
        // simulacaoRepository.delete(simulacao);

        // 5. Retornar o DTO de Resposta
        // Retorna o ContratoResponse.fromEntity(contratoSalvo);
        return new ContratoResponse( /* ... */ );
    }
}