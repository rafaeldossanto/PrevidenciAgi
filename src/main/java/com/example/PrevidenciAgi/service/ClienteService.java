package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.component.JwtTokenGenerator;
import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;

    public String login(String email, String senha) {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email não encontrado"));


        if (!passwordEncoder.matches(senha, cliente.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_CLIENTE")
        );

        Authentication authentication = new UsernamePasswordAuthenticationToken(cliente.getEmail(), null, authorities);

        return jwtTokenGenerator.generateToken(authentication);
    }

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
