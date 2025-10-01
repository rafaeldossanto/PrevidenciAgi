package com.example.PrevidenciAgi.controller;

import com.example.PrevidenciAgi.dto.ClienteDto;
import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.service.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/previdencia")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping("/criar")
    @Transactional
    public ClienteDto cadastrarCliente(@Valid @RequestBody Cliente cliente) {
        return clienteService.CadastrarCliente(cliente);
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
