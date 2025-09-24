package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.dto.contrato.request.ContratoRequest; // IMPORT FALTANTE 1
import com.example.PrevidenciAgi.dto.contrato.response.ContratoResponse; // IMPORT FALTANTE 2
import com.example.PrevidenciAgi.entity.ContratoPrevidencia;
import com.example.PrevidenciAgi.entity.Simulacao;
import com.example.PrevidenciAgi.repository.ContratoRepository; // IMPORT FALTANTE 3
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

        // Validação adicional: garante que a simulação pertença ao cliente logado (se você passar idCliente no Request)
        // if (!simulacao.getCliente().getId().equals(request.idCliente())) {
        //      throw new IllegalArgumentException("Simulação não pertence a este cliente.");
        // }

        // 2. Mapear Simulação para Contrato
        ContratoPrevidencia novoContrato = new ContratoPrevidencia();

        // Mapeamento dos dados do plano
        novoContrato.setCliente(simulacao.getCliente());
        novoContrato.setValorMensal(simulacao.getValorMensal());
        novoContrato.setTipoPlano(simulacao.getTipoContribuicao());
        // novoContrato.setRegimeTributario(simulacao.getRegimeTributario()); // (Remova o comentário se o campo existir na Simulação)

        // Mapeamento dos dados de Aposentadoria (Projeção)
        novoContrato.setDataAposentadoria(simulacao.getDataAposentar());
        novoContrato.setValorBeneficioEsperado(simulacao.getValorReceber());

        // Dados do Contrato
        novoContrato.setDataInicioContrato(LocalDate.now());
        novoContrato.setStatus("ASSINADO");

        // 3. Salvar o Contrato
        ContratoPrevidencia contratoSalvo = contratoRepository.save(novoContrato);

        // 4. Retornar o DTO de Resposta (Mapeamento de Entity -> Response)
        return new ContratoResponse(
                contratoSalvo.getId(), // Assumindo que a Entidade Contrato tem um getter getId()
                contratoSalvo.getTipoPlano(),
                contratoSalvo.getRegimeTributario(), // Assumindo que o campo existe
                contratoSalvo.getValorMensal(),
                contratoSalvo.getValorBeneficioEsperado(),
                contratoSalvo.getDataAposentadoria(),
                contratoSalvo.getDataInicioContrato(),
                contratoSalvo.getStatus()
        );
    }
}