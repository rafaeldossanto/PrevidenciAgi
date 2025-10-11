package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.component.JwtTokenGenerator;
import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.entity.Enum.AtualizacaoDeDados;
import com.example.PrevidenciAgi.entity.Enum.Role;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import com.example.PrevidenciAgi.service.exception.*;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;

    public void CadastrarCliente(Cliente cliente) {
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new JaExistente("Cliente cadastrado.");
        }
        if (clienteRepository.existsByEmail(cliente.getEmail())){
            throw new JaExistente("Esse email ja foi cadastrado");
        }
        if (cliente.getSenha().length() < 8) {
            throw new SenhaInvalida("Senha muito pequena, coloque uma senha segura");
        }
        if (!cliente.getGenero().getDeclaringClass().isEnum()) {
            throw new EscolhaEnumInvalida("Coloque um genero valido.");
        }
        if (!validacaoEmail(cliente.getEmail())) {
            throw new EmailInvalido("Coloque um email valido.");
        }

        String senhaCriptografada = passwordEncoder.encode(cliente.getSenha());
        cliente.setSenha(senhaCriptografada);
        cliente.setRole(Role.CLIENTE);

        clienteRepository.save(cliente);
    }

    public String login(String email, String senha) {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new NaoEncontrado("Email não encontrado"));


        if (!passwordEncoder.matches(senha, cliente.getSenha())) {
            throw new SenhaInvalida("Senha incorreta");
        }

        UserDetails userDetails = User.builder()
                .username(cliente.getEmail())
                .password(cliente.getSenha())
                .roles(cliente.getRole().name())
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        return jwtTokenGenerator.generateToken(authentication);
    }

    public void atualizarDados(Long id, AtualizacaoDeDados dado, String dadoNovo) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NaoEncontrado("Cliente com esse Id nao encontrado."));


        switch (dado) {
            case AtualizacaoDeDados.EMAIL:
                if (dadoNovo.equals(cliente.getEmail())) {
                    return;
                }
                cliente.setEmail(dadoNovo);
                break;

            case AtualizacaoDeDados.SENHA:
                String novaSenha = passwordEncoder.encode(dadoNovo);
                if (novaSenha.equals(cliente.getSenha())) {
                    return;
                } else if (novaSenha.length() < 8) {
                    throw new SenhaInvalida("senha muito pequena.");
                }
                cliente.setSenha(novaSenha);
                break;

            default:
                throw new EscolhaEnumInvalida("Campo '" + dado + "' não é válido para atualização. Use 'email' ou 'senha'");
        }
        clienteRepository.save(cliente);
    }

    private boolean validacaoEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    public Cliente dadosCliente(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new NaoEncontrado("Cliente nao encontrado."));
    }
}
