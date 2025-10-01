package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.model.cliente.Cliente;
import com.example.PrevidenciAgi.model.cliente.request.ClienteRequest;
import com.example.PrevidenciAgi.model.cliente.response.ClienteResponse;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Service;


@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    //Cadastrar cliente
    public ClienteResponse CadastrarCliente(ClienteRequest dados) {
        if (clienteRepository.existsByCpf(dados.cpf())) {
            throw new IllegalArgumentException("Cliente já cadastrado.");
        }
        Cliente cliente = new Cliente(dados);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return new ClienteResponse(clienteSalvo);
    }

    //Atualizar dados do cliente
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
