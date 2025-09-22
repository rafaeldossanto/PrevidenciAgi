package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void CadastrarCliente(Cliente cliente) {
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new IllegalArgumentException("Cliente cadastrado.");
        }

        String senhaCriptografada = passwordEncoder.encode(cliente.getSenha());
        cliente.setSenha(senhaCriptografada);

        clienteRepository.save(cliente);
    }

    public void atualizarDados(Long id, String dado, String dadoNovo) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityExistsException("Cliente com esse Id nao encontrado."));


        switch (dado.toLowerCase()) {
            case "email":
                cliente.setEmail(dadoNovo);
                break;

            case "senha":
                String novaSenha = passwordEncoder.encode(dadoNovo);
                cliente.setSenha(novaSenha);
                break;

            default:
                throw new IllegalArgumentException("Campo '" + dado + "' não é válido para atualização. Use 'email' ou 'senha'");
        }
        clienteRepository.save(cliente);
    }
}
