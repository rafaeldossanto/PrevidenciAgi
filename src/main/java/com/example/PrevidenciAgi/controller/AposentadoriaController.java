package com.example.PrevidenciAgi.controller;

import com.example.PrevidenciAgi.dto.aposentadoria.request.AposentadoriaRequest;
import com.example.PrevidenciAgi.dto.aposentadoria.response.AposentadoriaResponse;
import com.example.PrevidenciAgi.service.AposentadoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/aposentadorias")
@RequiredArgsConstructor
public class AposentadoriaController {

    // Injeção do serviço que contém a lógica de negócio
    private final AposentadoriaService aposentadoriaService;

    /**
     * Lista todas as aposentadorias cadastradas no sistema.
     * @return Lista de aposentadorias com status 200 OK
     */
    @GetMapping
    public ResponseEntity<List<AposentadoriaResponse>> listarTodas() {
        List<AposentadoriaResponse> aposentadorias = aposentadoriaService.listAll();
        return ResponseEntity.ok(aposentadorias);
    }

    /**
     * Busca uma aposentadoria específica pelo ID.
     * @param id ID da aposentadoria a ser buscada
     * @return Aposentadoria encontrada com status 200 OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<AposentadoriaResponse> buscarPorId(@PathVariable Long id) {
        AposentadoriaResponse aposentadoria = aposentadoriaService.findById(id);
        return ResponseEntity.ok(aposentadoria);
    }

    /**
     * Cria uma nova aposentadoria.
     * @param request Dados necessários para criar a aposentadoria
     * @return Aposentadoria criada com status 201 CREATED
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AposentadoriaResponse criar(@RequestBody @Valid AposentadoriaRequest request) {
        return aposentadoriaService.create(request);
    }

    /**
     * Atualiza uma aposentadoria existente.
     * @param id ID da aposentadoria a ser atualizada
     * @param request Novos dados da aposentadoria
     * @return Aposentadoria atualizada com status 200 OK
     */
    @PutMapping("/{id}")
    public AposentadoriaResponse atualizar(
            @PathVariable Long id,
            @RequestBody @Valid AposentadoriaRequest request) {
        return aposentadoriaService.update(id, request);
    }

    /**
     * Remove uma aposentadoria do sistema.
     * @param id ID da aposentadoria a ser removida
     * @return Resposta vazia com status 204 NO CONTENT
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        aposentadoriaService.delete(id);
    }
}
