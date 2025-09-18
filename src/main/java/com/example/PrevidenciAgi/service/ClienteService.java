package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {
    private ClienteRepository clienteRepository;

    public Optional<Cliente> CadastrarCliente(Cliente cliente){
        if (clienteRepository.existsByCpf(cliente.getCpf())){
            throw new IllegalArgumentException("Cliente cadastrado.");
        }
        clienteRepository.save(cliente);
        return Optional.of(cliente);
    }

    public void deletarCliente(Long id){
        if (!clienteRepository.existsById(id)){
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        clienteRepository.deleteById(id);
    }
}
