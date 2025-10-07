package com.example.PrevidenciAgi.controller;


import com.example.PrevidenciAgi.model.simulacao.request.SimulacaoRequest;
import com.example.PrevidenciAgi.service.SimulacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/simulacao")
public class SimulacaoController {

    @Autowired
    private SimulacaoService simulacaoService;

    @PostMapping("/calculo")
    public void calcularSimulacao(@RequestBody SimulacaoRequest request) {
        simulacaoService.calcularSimulacao(request);
    }
}
