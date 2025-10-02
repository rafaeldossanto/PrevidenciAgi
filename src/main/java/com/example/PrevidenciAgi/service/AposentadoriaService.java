package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.dto.aposentadoria.request.AposentadoriaRequest;
import com.example.PrevidenciAgi.entity.Aposentadoria;
import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.repository.AposentadoriaRepository;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import com.example.PrevidenciAgi.service.exception.JaExistente;
import com.example.PrevidenciAgi.service.exception.NaoEncontrado;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class AposentadoriaService {
    @Autowired
    private AposentadoriaRepository aposentadoriaRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    public Aposentadoria assinarAposentadoria(AposentadoriaRequest request){
        if (aposentadoriaRepository.existsByClienteId(request.id())){
            throw new JaExistente("Cliente ja possui aposentadoria cadastrada");
        }

        Cliente cliente = clienteRepository.findById(request.id())
                .orElseThrow(() -> new NaoEncontrado("Cliente com esse id nao encontrado."));

        Aposentadoria aposentadoria = new Aposentadoria();
        aposentadoria.setTipoAposentadoria(request.tipoAposentadoria());
        aposentadoria.setValor_mensal(request.valor_mensal());
        aposentadoria.setValor_deposito(request.valor_deposito());
        aposentadoria.setData_aposentar(request.data_aposentar());
        aposentadoria.setData_inicio(request.data_inicio());
        aposentadoria.setData_contratada(LocalDate.now());
        aposentadoria.setCliente(cliente);

        cliente.setAposentadoria(aposentadoria);
        clienteRepository.save(cliente);

        return aposentadoriaRepository.save(aposentadoria);
    }


    public void ajustarValorMensal(Long id, Double novoValor){
        Aposentadoria aposentadoria = aposentadoriaRepository.findById(id)
                .orElseThrow(() -> new NaoEncontrado("Aposentadoria com esse id nao encontrada."));

        if (novoValor < 0){
            throw new IllegalArgumentException("O valor mensal nao pode ser negativo.");
        }
        if (novoValor.equals(aposentadoria.getValor_mensal())){
            return;
        }

        aposentadoria.setValor_mensal(novoValor);
        aposentadoriaRepository.save(aposentadoria);
        log.info("Valor mensal da aposentadoria atualizado para {}", novoValor);
    }
}
