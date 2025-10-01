package com.example.PrevidenciAgi.controller;

import com.example.PrevidenciAgi.model.cliente.request.ClienteRequest;
import com.example.PrevidenciAgi.model.cliente.response.ClienteResponse;
import com.example.PrevidenciAgi.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/previdencia")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    //Cadastrar cliente
    @PostMapping("/criar")
    @Transactional
    public ResponseEntity<ClienteResponse> cadastrarCliente(@Valid @RequestBody ClienteRequest dados) {
        ClienteResponse response = clienteService.CadastrarCliente(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarDados(
            @PathVariable Long id,
            @RequestBody Map<String, String> dadosAtualizacao) {

        if (!dadosAtualizacao.containsKey("campo") || !dadosAtualizacao.containsKey("valor")) {
            return ResponseEntity.badRequest()
                    .body("JSON deve conter 'campo' e 'valor'");
        }

        String campo = dadosAtualizacao.get("campo");
        String valor = dadosAtualizacao.get("valor");

        String resultado = clienteService.atualizarDados(id, campo, valor);
        return ResponseEntity.ok(resultado);
    }
}
