package com.example.PrevidenciAgi.controller;

import com.example.PrevidenciAgi.model.cliente.request.AtualizarDadosRequest;
import com.example.PrevidenciAgi.model.cliente.request.LoginRequest;
import com.example.PrevidenciAgi.model.cliente.Cliente;
import com.example.PrevidenciAgi.service.ClienteService;
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

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest login) {
        String token = clienteService.login(login.email(), login.senha());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "message", "Login realizado com sucesso"
        ));
    }

    @PostMapping("/criar")
    @Transactional
    public void cadastrarCliente(@Valid @RequestBody Cliente cliente) {
        clienteService.CadastrarCliente(cliente);
    }

    @PutMapping("/atualizar/{id}")
    public void atualizarDados(@PathVariable Long id, @RequestBody AtualizarDadosRequest request) {
        clienteService.atualizarDados(id, request.dado(), request.mudanca());
    }
}
