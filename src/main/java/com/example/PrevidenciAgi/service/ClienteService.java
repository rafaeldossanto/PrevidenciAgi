package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.dto.cliente.response.DadosCadastroResponse;
import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ClienteService {
    @Autowired
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public DadosCadastroResponse CadastrarCliente(@Valid Cliente dados){
        Cliente cliente = clienteRepository.save(new Cliente(dados));
        return new DadosCadastroResponse(cliente);
    }

    public void deletarCliente(Long id){
        if (!clienteRepository.existsById(id)){
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        clienteRepository.deleteById(id);
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
