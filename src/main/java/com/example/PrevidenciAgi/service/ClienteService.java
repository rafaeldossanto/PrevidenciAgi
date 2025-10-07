package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.component.JwtTokenGenerator;
import com.example.PrevidenciAgi.dto.cliente.Cliente;
import com.example.PrevidenciAgi.Enum.AtualizacaoDeDados;
import com.example.PrevidenciAgi.Enum.Role;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import com.example.PrevidenciAgi.service.exception.EscolhaEnumInvalida;
import com.example.PrevidenciAgi.service.exception.JaExistente;
import com.example.PrevidenciAgi.service.exception.NaoEncontrado;
import com.example.PrevidenciAgi.service.exception.SenhaInvalida;
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

    public void CadastrarCliente(Cliente cliente) {
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new JaExistente("Cliente cadastrado.");
        }
        if (cliente.getSenha().length() < 8){
            throw new SenhaInvalida("Senha muito pequena, coloque uma senha segura");
        }

        if (!cliente.getGenero().getDeclaringClass().isEnum()){
            throw new EscolhaEnumInvalida("Coloque um genero valido.");
        }

        String senhaCriptografada = passwordEncoder.encode(cliente.getSenha());
        cliente.setSenha(senhaCriptografada);
        cliente.setRole(Role.CLIENTE);

        clienteRepository.save(cliente);
    }

    public void atualizarDados(Long id, AtualizacaoDeDados dado, String dadoNovo) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NaoEncontrado("Cliente com esse Id nao encontrado."));


        switch (dado) {
            case AtualizacaoDeDados.EMAIL:
                if (dadoNovo.equals(cliente.getEmail())){
                    return;
                }
                cliente.setEmail(dadoNovo);
                break;

            case AtualizacaoDeDados.SENHA:
                String novaSenha = passwordEncoder.encode(dadoNovo);
                if (novaSenha.equals(cliente.getSenha())){
                    return;
                } else if (novaSenha.length() < 8){
                    throw new SenhaInvalida("senha muito pequena.");
                }
                cliente.setSenha(novaSenha);
                break;

            default:
                throw new EscolhaEnumInvalida("Campo '" + dado + "' não é válido para atualização. Use 'email' ou 'senha'");
        }
        clienteRepository.save(cliente);
    }
}
