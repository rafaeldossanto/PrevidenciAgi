package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.dto.simulacao.request.SimulacaoRequest;
import com.example.PrevidenciAgi.dto.simulacao.response.SimulacaoResponse;
import com.example.PrevidenciAgi.entity.Cliente; // Import necessário
import com.example.PrevidenciAgi.entity.Simulacao;
import com.example.PrevidenciAgi.repository.ClienteRepository; // Import necessário
import com.example.PrevidenciAgi.repository.SimulacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate; // Import necessário

@Service
public class SimulacaoService {

    private final SimulacaoRepository simulacaoRepository;
    private final ClienteRepository clienteRepository; // Adicionado para buscar o cliente
    private final PGBLService pgblService;
    private final VGBLService vgblService;

    // Construtor CORRIGIDO e COMPLETO
    public SimulacaoService(
            SimulacaoRepository simulacaoRepository,
            ClienteRepository clienteRepository,
            PGBLService pgblService,
            VGBLService vgblService) {
        this.simulacaoRepository = simulacaoRepository;
        this.clienteRepository = clienteRepository;
        this.pgblService = pgblService;
        this.vgblService = vgblService;
    }

    @Transactional
    public SimulacaoResponse realizarSimulacao(SimulacaoRequest request) {

        // 1. Determinar o Serviço de Cálculo (PGBL ou VGBL)
        double valorLiquido;
        String tipoPlano = request.tipoContribuicao();
        String regimeTributario = request.regimeTributario(); // Pego do Request

        if ("PGBL".equalsIgnoreCase(tipoPlano)) {
            // Chama PGBLService com o regime de tributação
            valorLiquido = pgblService.calcularResgateLiquido(request, regimeTributario);
        } else if ("VGBL".equalsIgnoreCase(tipoPlano)) {
            // Chama VGBLService com o regime de tributação
            valorLiquido = vgblService.calcularResgateLiquido(request, regimeTributario);
        } else {
            throw new IllegalArgumentException("Tipo de contribuição inválido.");
        }

        // 2. Buscar o Cliente (necessário para o relacionamento JPA)
        Cliente cliente = clienteRepository.findById(request.idCliente())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + request.idCliente()));

        // 3. Criar a Entidade Simulacao (Rascunho)
        Simulacao novaSimulacao = new Simulacao();

        // Mapeamento dos dados do Request para a Entidade
        novaSimulacao.setCliente(cliente);
        novaSimulacao.setValorMensal(request.valorMensal());
        novaSimulacao.setValorReceber(valorLiquido);
        novaSimulacao.setGenero(request.genero());
        novaSimulacao.setTipoContribuicao(tipoPlano);
        // Assumindo que o campo regimeTributario existe na Entidade Simulacao, adicione:
        // novaSimulacao.setRegimeTributario(regimeTributario);
        novaSimulacao.setDataInicial(request.dataInicial());
        novaSimulacao.setDataAposentar(request.dataAposentar());
        novaSimulacao.setTempoContribuicao(request.tempoContribuicao());
        novaSimulacao.setTempoRecebimento(request.tempoRecebimento());

        // 4. Salvar Simulação (Rascunho)
        Simulacao simulacaoSalva = simulacaoRepository.save(novaSimulacao);

        // 5. Retornar DTO de Resposta
        return new SimulacaoResponse(
                simulacaoSalva.getIdSimulacao(),
                simulacaoSalva.getValorMensal(),
                simulacaoSalva.getValorReceber(),
                simulacaoSalva.getGenero(),
                simulacaoSalva.getTipoContribuicao(),
                simulacaoSalva.getDataInicial(),
                simulacaoSalva.getDataAposentar(),
                simulacaoSalva.getTempoContribuicao(),
                simulacaoSalva.getTempoRecebimento()
        );
    }
}