package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.dto.simulacao.request.SimulacaoRequest;
import com.example.PrevidenciAgi.dto.simulacao.response.SimulacaoResponse;
import com.example.PrevidenciAgi.entity.Simulacao;
import com.example.PrevidenciAgi.repository.SimulacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimulacaoService {

    // Repositórios e Serviços (Injeção via Construtor)
    private final SimulacaoRepository simulacaoRepository;
    private final PGBLService pgblService;
    private final VGBLService vgblService;
    // ... ClienteRepository para buscar o Cliente

    // Construtor
    public SimulacaoService(/* ... */) { /* ... */ }

    @Transactional
    public SimulacaoResponse realizarSimulacao(SimulacaoRequest request) {

        // 1. Determinar o Serviço de Cálculo (PGBL ou VGBL)
        double valorLiquido;
        String tipoPlano = request.tipoContribuicao(); // PGBL ou VGBL

        if ("PGBL".equalsIgnoreCase(tipoPlano)) {
            // A implementação de PGBLService precisa ser injetada com a tributação correta
            valorLiquido = pgblService.calcularResgateLiquido(request);
        } else if ("VGBL".equalsIgnoreCase(tipoPlano)) {
            // A implementação de VGBLService precisa ser injetada com a tributação correta
            valorLiquido = vgblService.calcularResgateLiquido(request);
        } else {
            throw new IllegalArgumentException("Tipo de contribuição inválido.");
        }

        // 2. Criar a Entidade Simulacao (Rascunho)
        Simulacao novaSimulacao = new Simulacao();

        // Mapeia os dados do Request
        // ... (Mapear todos os campos do Request para a Entidade)
        novaSimulacao.setValorReceber(valorLiquido); // Salva o resultado
        // ... (Buscar e setar o Cliente)

        // 3. Salvar Simulação (Rascunho)
        Simulacao simulacaoSalva = simulacaoRepository.save(novaSimulacao);

        // 4. Retornar DTO de Resposta
        // Retorna o resultado com o ID gerado (para futura contratação)
        // SimulacaoResponse.fromEntity(simulacaoSalva);
        return new SimulacaoResponse( /* ... */ );
    }
}