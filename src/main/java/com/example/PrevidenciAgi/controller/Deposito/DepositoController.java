package com.example.PrevidenciAgi.controller.Deposito;

import com.example.PrevidenciAgi.dto.deposito.request.DepositosRequest;
import com.example.PrevidenciAgi.dto.deposito.response.DepositosResponse;
import com.example.PrevidenciAgi.entity.Depositos;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import com.example.PrevidenciAgi.service.DepositosService.DepositosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deposito")
public class DepositoController {
    @Autowired
    private DepositosService depositosService;

    @PostMapping("/depositar/{id}")
    public ResponseEntity<DepositosRequest> depositar(
            @PathVariable Long id,
            @RequestBody DepositosRequest request){

        DepositosRequest resposta = depositosService.depositar(id, request);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<List<DepositosResponse>> listarDepositos(@PathVariable Long id){
        return ResponseEntity.ok(depositosService.listarDepositos(id));
    }

    @GetMapping("/listar/{id}/{idUnico}")
    public ResponseEntity<DepositosResponse> listarDepositoUnico(@PathVariable Long id,
                                                         @PathVariable Long idUnico){
        Depositos deposito = depositosService.listarUnico(id, idUnico);
        DepositosResponse response = new DepositosResponse(deposito);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/rdepositar/{id}")
    public ResponseEntity<DepositosResponse> depositoAuto(@PathVariable Long id, @RequestBody DepositosRequest request){
        Depositos deposito = depositosService.realizarDeposito(id, request);
        DepositosResponse resposta = DepositosResponse.fromEntity(deposito);
        return ResponseEntity.ok(resposta);
    }
}
