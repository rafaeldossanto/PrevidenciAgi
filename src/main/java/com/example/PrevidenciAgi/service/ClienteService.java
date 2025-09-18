package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.dto.ClienteDto;
import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteDto CadastrarCliente(Cliente cliente){
        if (clienteRepository.existsByCpf(cliente.getCpf())){
            throw new IllegalArgumentException("Cliente cadastrado.");
        }

        Cliente clientedto = clienteRepository.save(cliente);
        return ClienteDto.fromCliente(clientedto);
    }

    public void deletarCliente(Long id){
        if (!clienteRepository.existsById(id)){
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        clienteRepository.deleteById(id);
    }
}
