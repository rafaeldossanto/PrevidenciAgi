package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.dto.deposito.request.DepositosRequest;
import com.example.PrevidenciAgi.entity.Aposentadoria;
import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.entity.Depositos;
import com.example.PrevidenciAgi.repository.AposentadoriaRepository;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import com.example.PrevidenciAgi.repository.DepositosRepository;
import com.example.PrevidenciAgi.service.exception.NaoEncontrado;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new NaoEncontrado("Aposentadoria nao encontrada"));

        deposito.setDataDeposito(LocalDateTime.now());
        deposito.setValor(request.valor());
        deposito.setAposentadoria(aposentadoria);
        deposito.setCliente(aposentadoria.getCliente());
        deposito.setSaldo(deposito.getSaldo() + request.valor());

        depositosRepository.save(deposito);

        return deposito;
    }

    public Map<LocalDateTime,Double> listarDepositos(Long id){
        return clienteRepository.findById(id)
                .map(Cliente::getDepositos)
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.toMap(
                        Depositos::getDataDeposito,
                        Depositos::getValor
                ));
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