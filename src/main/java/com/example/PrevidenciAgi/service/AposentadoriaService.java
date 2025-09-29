package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.dto.aposentadoria.request.AposentadoriaRequest;
import com.example.PrevidenciAgi.entity.Aposentadoria;
import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.repository.AposentadoriaRepository;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import com.example.PrevidenciAgi.service.exception.JaExistente;
import com.example.PrevidenciAgi.service.exception.NaoEncontrado;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

        return aposentadoriaRepository.save(aposentadoria);
    }
}
