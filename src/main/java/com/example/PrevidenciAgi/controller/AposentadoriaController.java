package com.example.PrevidenciAgi.controller;

import com.example.PrevidenciAgi.dto.aposentadoria.request.AposentadoriaRequest;
import com.example.PrevidenciAgi.dto.aposentadoria.response.AposentadoriaResponse;
import com.example.PrevidenciAgi.service.AposentadoriaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aposentadoria")
public class AposentadoriaController {

    private final AposentadoriaService aposentadoriaService;

    public AposentadoriaController(AposentadoriaService aposentadoriaService) {
        this.aposentadoriaService = aposentadoriaService;
    }

    @PostMapping("/criar")
    @Transactional
    public ResponseEntity<AposentadoriaResponse> caluculoAposentadoria (@Valid @RequestBody AposentadoriaRequest dados){
        AposentadoriaResponse response = aposentadoriaService.calcularAposentadoria(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
