package com.example.PrevidenciAgi.controller;

import com.example.PrevidenciAgi.dto.cliente.request.LoginRequest;
import com.example.PrevidenciAgi.entity.Cliente;
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
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest login){
        String token = clienteService.login(login.email(), login.senha());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "message", "Login realizado com sucesso"
        ));
    }

    @PostMapping("/criar")
    @Transactional
    public ResponseEntity<String> cadastrarCliente(@Valid @RequestBody Cliente cliente) {
        clienteService.CadastrarCliente(cliente);
        return ResponseEntity.ok("Cliente cadastrado com sucesso!");
    }

    @PutMapping("/atualizar/{id}")
    public void atualizarDados(@PathVariable Long id, @RequestBody String dado, @RequestBody String dadoNovo){
        clienteService.atualizarDados(id, dado, dadoNovo);
    }
}
