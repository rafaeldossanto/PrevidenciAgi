package com.example.PrevidenciAgi.controller;

import com.example.PrevidenciAgi.dto.ClienteDto;
import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/previdencia")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping("/criar")
    public ClienteDto cadastrarCliente(@RequestBody Cliente cliente){
        return clienteService.CadastrarCliente(cliente);
    }

    @DeleteMapping("/deletar/{id}")
    public void deletarCliente(@PathVariable Long id){
        clienteService.deletarCliente(id);
    }
}
