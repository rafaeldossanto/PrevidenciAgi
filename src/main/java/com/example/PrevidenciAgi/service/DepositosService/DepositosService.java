package com.example.PrevidenciAgi.service.DepositosService;


import com.example.PrevidenciAgi.dto.deposito.request.DepositosRequest;
import com.example.PrevidenciAgi.dto.deposito.response.DepositosResponse;
import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.entity.Depositos;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import com.example.PrevidenciAgi.repository.DepositosRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepositosService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private DepositosRepository depositosRepository;

    public DepositosRequest depositar(Long clienteId, DepositosRequest request){
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntityExistsException("Cliente com esse Id nao encontrado."));

        Depositos deposito = new Depositos();
        deposito.setCliente(cliente);
        deposito.setValor(request.valor());

        depositosRepository.save(deposito);

        return new DepositosRequest(deposito.getValor());
    }

    public List<DepositosResponse> listarDepositos(Long clienteId){
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntityExistsException("Cliente com esse Id nao encontrado."));

        return depositosRepository.findByCliente(cliente).stream()
                .map(deposito -> new DepositosResponse(
                        deposito.getIdDeposito(),
                        deposito.getTipo(),
                        deposito.getValor(),
                        deposito.getDataDeposito()
                ))
                .toList();
    }


}
