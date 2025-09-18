package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.dto.ClienteDto;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {
    private ClienteRepository clienteRepository;

    public Optional<ClienteDto> CadastrarCliente(ClienteDto cliente){
        if (clienteRepository.existsByCpf(cliente.getCpf())){
            throw new IllegalArgumentException("Cliente cadastrado.");
        }
        clienteRepository.save(cliente);
        return Optional.of(cliente);
    }
}
