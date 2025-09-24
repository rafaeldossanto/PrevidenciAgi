package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.dto.aposentadoria.request.AposentadoriaRequest;
import com.example.PrevidenciAgi.dto.aposentadoria.response.AposentadoriaResponse;
import com.example.PrevidenciAgi.entity.Aposentadoria;
import com.example.PrevidenciAgi.repository.AposentadoriaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class AposentadoriaService {

    private final AposentadoriaRepository aposentadoriaRepository;

    public AposentadoriaService(AposentadoriaRepository aposentadoriaRepository) {
        this.aposentadoriaRepository = aposentadoriaRepository;
    }

    public AposentadoriaResponse calcularAposentadoria(@Valid AposentadoriaRequest dados) {
        Aposentadoria aposentadoria = new Aposentadoria(dados);
        aposentadoriaRepository.save(aposentadoria);
        return new AposentadoriaResponse(aposentadoria);
    }

}
