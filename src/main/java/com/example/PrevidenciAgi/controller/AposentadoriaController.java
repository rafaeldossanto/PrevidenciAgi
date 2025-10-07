package com.example.PrevidenciAgi.controller;

import com.example.PrevidenciAgi.dto.aposentadoria.request.AposentadoriaRequest;
import com.example.PrevidenciAgi.dto.aposentadoria.Aposentadoria;
import com.example.PrevidenciAgi.service.AposentadoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aposentadoria")
public class AposentadoriaController {
    @Autowired
    private AposentadoriaService aposentadoriaService;

    @PostMapping("/assinar")
    public Aposentadoria assinarAposentadoria(@RequestBody AposentadoriaRequest request){
        return aposentadoriaService.assinarAposentadoria(request);
    }

    @PutMapping("/ajuste/{id}")
    public void ajustarValorMensal(@PathVariable Long id,@RequestParam Double novoValor){
        aposentadoriaService.ajustarValorMensal(id, novoValor);
    }
}
