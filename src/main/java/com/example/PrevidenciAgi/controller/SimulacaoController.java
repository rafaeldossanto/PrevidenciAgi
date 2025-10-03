package com.example.PrevidenciAgi.controller;

import com.example.PrevidenciAgi.dto.simulacao.request.SimulacaoRequest;
import com.example.PrevidenciAgi.dto.simulacao.response.SimulacaoResponse;
import com.example.PrevidenciAgi.entity.Simulacao;
import com.example.PrevidenciAgi.mapper.SimulacaoMapper;
import com.example.PrevidenciAgi.repository.SimulacaoRepository;
import com.example.PrevidenciAgi.service.SimulacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/simulacoes")
public class SimulacaoController {

    private final SimulacaoService simulacaoService;
    private final SimulacaoRepository simulacaoRepository;

    public SimulacaoController(SimulacaoService simulacaoService, SimulacaoRepository simulacaoRepository) {
        this.simulacaoService = simulacaoService;
        this.simulacaoRepository = simulacaoRepository;
    }

    // calcula, mas não salva
    @PostMapping("/calculate")
    public ResponseEntity<SimulacaoResponse> calcular(@RequestBody @Valid SimulacaoRequest request) {
        Simulacao simulacao = simulacaoService.calcularSimulacao(request);
        return ResponseEntity.ok(SimulacaoMapper.toResponse(simulacao));
    }

    // calcula e salva
    @PostMapping
    public ResponseEntity<SimulacaoResponse> criar(@RequestBody @Valid SimulacaoRequest request) {
        Simulacao simulacao = simulacaoService.calcularSimulacao(request);
        Simulacao salvo = simulacaoService.salvarSimulacao(simulacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(SimulacaoMapper.toResponse(salvo));
    }

    @GetMapping
    public ResponseEntity<List<SimulacaoResponse>> listar() {
        List<SimulacaoResponse> list = simulacaoRepository.findAll()
                .stream()
                .map(SimulacaoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimulacaoResponse> buscar(@PathVariable Long id) {
        return simulacaoRepository.findById(id)
                .map(sim -> ResponseEntity.ok(SimulacaoMapper.toResponse(sim)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!simulacaoRepository.existsById(id)) return ResponseEntity.notFound().build();
        simulacaoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}