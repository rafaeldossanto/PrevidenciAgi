package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.dto.aposentadoria.request.AposentadoriaRequest;
import com.example.PrevidenciAgi.dto.deposito.request.DepositosRequest;
import com.example.PrevidenciAgi.entity.Aposentadoria;
import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.entity.Depositos;
import com.example.PrevidenciAgi.repository.AposentadoriaRepository;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import com.example.PrevidenciAgi.repository.DepositosRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DepositosService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private AposentadoriaRepository aposentadoriaRepository;
    @Autowired
    private DepositosRepository depositosRepository;

    public Depositos depositar(DepositosRequest request){
        Depositos deposito = new Depositos();
        Aposentadoria aposentadoria = aposentadoriaRepository.findById(request.id_aposentadoria())
                        .orElseThrow(() -> new EntityNotFoundException("Aposentadoria nao encontrada"));

        deposito.setDataDeposito(LocalDateTime.now());
        deposito.setValor(request.valor());
        deposito.setAposentadoria(aposentadoria);
        deposito.setCliente(aposentadoria.getCliente());

        depositosRepository.save(deposito);

        return deposito;
    }

    public Double totalDoCliente(Long id){
        List<Depositos> depositos = depositosRepository.findByClienteId(id);

        return depositos.stream()
                .mapToDouble(Depositos::getValor)
                .sum();
    }

    public Double totalDaAposentadoria(Long id){
        List<Depositos> depositos = depositosRepository.findByAposentadoriaIdAposentadoria(id);

        return  depositos.stream()
                .mapToDouble(Depositos::getValor)
                .sum();
    }
}
