package com.example.PrevidenciAgi.controller;

import com.example.PrevidenciAgi.dto.deposito.request.DepositosRequest;
import com.example.PrevidenciAgi.dto.deposito.Depositos;
import com.example.PrevidenciAgi.service.DepositosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/depositos")
public class DepositosController {

    @Autowired
    private DepositosService depositosService;

    @PostMapping
    public Depositos depositar(@RequestBody DepositosRequest request){
        return depositosService.depositar(request);
    }

    @GetMapping("/{id}")
    public List<Double> listarDepositosAposentadoria(@PathVariable Long id){
        return depositosService.listarDepositos(id);
    }
}
