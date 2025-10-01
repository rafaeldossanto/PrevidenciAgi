package com.example.PrevidenciAgi.controller;

import com.example.PrevidenciAgi.dto.aposentadoria.request.AposentadoriaRequest;
import com.example.PrevidenciAgi.entity.Aposentadoria;
import com.example.PrevidenciAgi.service.AposentadoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aposentadoria")
public class AposentadoriaController {
    @Autowired
    private AposentadoriaService aposentadoriaService;

    @PostMapping("/assinar")
    public Aposentadoria assinarAposentadoria(@RequestBody AposentadoriaRequest request){
        return aposentadoriaService.assinarAposentadoria(request);
    }
}
