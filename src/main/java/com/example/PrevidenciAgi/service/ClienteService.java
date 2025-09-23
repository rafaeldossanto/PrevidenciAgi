package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.dto.cliente.request.DadosCadastroRequest;
import com.example.PrevidenciAgi.dto.cliente.response.DadosCadastroResponse;
import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public DadosCadastroResponse CadastrarCliente(DadosCadastroRequest dados){
        Cliente cliente = clienteRepository.save(new Cliente(dados)); // Entidade
        return new DadosCadastroResponse(cliente); // DTO
    }

    public String atualizarDados(Long id, String dado, String dadoNovo) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityExistsException("Cliente com esse Id nao encontrado."));

        String dadoAntigo;

        switch (dado.toLowerCase()) {
            case "email":
                dadoAntigo = cliente.getEmail();
                cliente.setEmail(dadoNovo);
                break;

            case "senha":
                dadoAntigo = cliente.getSenha();
                cliente.setSenha(dadoNovo);
                break;

            default:
                throw new IllegalArgumentException("Campo '" + dado + "' não é válido para atualização. Use 'email' ou 'senha'");
        }
        clienteRepository.save(cliente);
        return dado + " alterado de: " + dadoAntigo + " para: " + dadoNovo;
    }
}
