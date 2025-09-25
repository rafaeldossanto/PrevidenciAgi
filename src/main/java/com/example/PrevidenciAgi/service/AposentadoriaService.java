package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.dto.aposentadoria.request.AposentadoriaRequest;
import com.example.PrevidenciAgi.dto.aposentadoria.response.AposentadoriaResponse;
import com.example.PrevidenciAgi.entity.Aposentadoria;
import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.repository.AposentadoriaRepository;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AposentadoriaService {

    private final AposentadoriaRepository aposentadoriaRepository;
    private final ClienteRepository clienteRepository;

    public List<AposentadoriaResponse> listAll() {
        return aposentadoriaRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public AposentadoriaResponse findById(Long id) {
        return aposentadoriaRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> notFound("Aposentadoria não encontrada"));
    }

    @Transactional
    public AposentadoriaResponse create(AposentadoriaRequest request) {
        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> notFound("Cliente não encontrado"));
                
        Aposentadoria aposentadoria = new Aposentadoria();
        aposentadoria.setCliente(cliente);
        aposentadoria.setTipoAposentadoria(request.tipoAposentadoria());
        aposentadoria.setDataAposentar(request.dataAposentar());
        aposentadoria.setDataContratada(request.dataContratada());
        
        Aposentadoria aposentadoriaSalva = aposentadoriaRepository.save(aposentadoria);
        return toResponse(aposentadoriaSalva);
    }

    @Transactional
    public AposentadoriaResponse update(Long id, AposentadoriaRequest request) {
        Aposentadoria aposentadoria = aposentadoriaRepository.findById(id)
                .orElseThrow(() -> notFound("Aposentadoria não encontrada"));
                
        aposentadoria.setTipoAposentadoria(request.tipoAposentadoria());
        aposentadoria.setDataAposentar(request.dataAposentar());
        aposentadoria.setDataContratada(request.dataContratada());
        aposentadoria.setValorDeposito(request.valorDeposito());
        
        Aposentadoria aposentadoriaAtualizada = aposentadoriaRepository.save(aposentadoria);
        return toResponse(aposentadoriaAtualizada);
    }

    @Transactional
    public void delete(Long id) {
        if (!aposentadoriaRepository.existsById(id)) {
            throw notFound("Aposentadoria não encontrada");
        }
        aposentadoriaRepository.deleteById(id);
    }

    private AposentadoriaResponse toResponse(Aposentadoria aposentadoria) {
        return new AposentadoriaResponse(
            aposentadoria.getIdAposentadoria(),
            aposentadoria.getTipoAposentadoria(),
            aposentadoria.getDataAposentar(),
            aposentadoria.getDataContratada(),
            aposentadoria.getValorDeposito()
        );
    }

    private EntityNotFoundException notFound(String message) {
        return new EntityNotFoundException(message);
    }
}