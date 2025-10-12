package com.example.PrevidenciAgi.controller;

import com.example.PrevidenciAgi.dto.cliente.request.AtualizarDadosRequest;
import com.example.PrevidenciAgi.dto.cliente.request.DadosRequest;
import com.example.PrevidenciAgi.dto.cliente.request.LoginRequest;
import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.service.ClienteService;
import com.example.PrevidenciAgi.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/previdencia")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest login){
        Map<String, Object> resultado = clienteService.loginAlt(login.email(), login.senha());
        String token = (String) resultado.get("token");
        Cliente cliente = (Cliente) resultado.get("cliente");

        return ResponseEntity.ok(Map.of(
                "token", token,
                "clienteId", cliente.getId().toString(),
                "message", "Login realizado com sucesso"
        ));
    }

    @PostMapping("/criar")
    @Transactional
    public void cadastrarCliente(@Valid @RequestBody Cliente cliente) {
        clienteService.CadastrarCliente(cliente);
    }

    @PutMapping("/atualizar/{id}")
    public void atualizarDados(@PathVariable Long id, @RequestBody AtualizarDadosRequest request){
        clienteService.atualizarDados(id, request.dado(), request.mudanca());
    }

    @PostMapping("/recuperar")
    public void recuperarSenha(@RequestBody String email){
        emailService.recuperacaoSenha(email);
    }

    @GetMapping("/dados/{id}")
    public DadosRequest dadosCliente(@PathVariable Long id){
        return clienteService.dadosCliente(id);
    }
}
