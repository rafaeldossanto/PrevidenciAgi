package com.example.PrevidenciAgi.controller;

import com.example.PrevidenciAgi.dto.deposito.request.DepositosRequest;
import com.example.PrevidenciAgi.entity.Depositos;
import com.example.PrevidenciAgi.service.DepositosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/depositos")
public class DepositosController {

    @Autowired
    private DepositosService depositosService;

    @PostMapping
    public Depositos depositar(@RequestBody DepositosRequest request){
        return depositosService.depositar(request);
    }
}
