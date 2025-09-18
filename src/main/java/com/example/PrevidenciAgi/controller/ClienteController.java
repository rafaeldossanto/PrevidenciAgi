package com.example.PrevidenciAgi.controller;

import com.example.PrevidenciAgi.dto.ClienteDto;
import com.example.PrevidenciAgi.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/previdencia")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping("/criar")
    public Optional<ClienteDto> cadastrarCliente(@RequestBody ClienteDto clienteDto){
        return clienteService.CadastrarCliente(clienteDto);
    }
}
