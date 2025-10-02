package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.dto.deposito.request.DepositosRequest;
import com.example.PrevidenciAgi.entity.Aposentadoria;
import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.entity.Depositos;
import com.example.PrevidenciAgi.entity.Enum.TipoDeposito;
import com.example.PrevidenciAgi.repository.AposentadoriaRepository;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import com.example.PrevidenciAgi.repository.DepositosRepository;
import com.example.PrevidenciAgi.service.exception.NaoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepositosService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private AposentadoriaRepository aposentadoriaRepository;
    @Autowired
    private DepositosRepository depositosRepository;

    public Depositos depositar(DepositosRequest request) {
        Aposentadoria aposentadoria = aposentadoriaRepository.findById(request.id_aposentadoria())
                .orElseThrow(() -> new NaoEncontrado("Aposentadoria nao encontrada"));

        Cliente cliente = aposentadoria.getCliente();
        LocalDateTime agora = LocalDateTime.now();

        Double ultimoSaldo = depositosRepository.findTopByClienteIdOrderByDataDepositoDesc(cliente.getId())
                .map(Depositos::getSaldo)
                .orElse(0.0);
        Double novoSaldo = ultimoSaldo + request.valor();

        LocalDateTime inicioMes = agora.toLocalDate().withDayOfMonth(1).atStartOfDay();
        LocalDateTime fimMes = agora.toLocalDate().withDayOfMonth(agora.toLocalDate().lengthOfMonth()).atTime(23, 59, 59);

        Double totalDepositadoMes = depositosRepository.findTotalDepositadoNoPeriodo(cliente.getId(), inicioMes, fimMes);
        if (totalDepositadoMes == null) {
            totalDepositadoMes = 0.0;
        }

        Double totalAposDeposito = totalDepositadoMes + request.valor();

        Depositos deposito = new Depositos();
        deposito.setDataDeposito(agora);
        deposito.setValor(request.valor());
        deposito.setAposentadoria(aposentadoria);
        deposito.setCliente(cliente);
        deposito.setSaldo(novoSaldo);

        if (totalAposDeposito > aposentadoria.getValor_mensal()) {
            deposito.setTipo(TipoDeposito.APORTE);
        } else {
            deposito.setTipo(TipoDeposito.MENSAL);
        }

        return depositosRepository.save(deposito);
    }


    public List<Double> listarDepositos(Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> cliente.getDepositos().stream()
                        .map(Depositos::getValor)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    public Double totalDoCliente(Long id) {
        List<Depositos> depositos = depositosRepository.findByClienteId(id);

        return depositos.stream()
                .mapToDouble(Depositos::getValor)
                .sum();
    }

    public Double totalDaAposentadoria(Long id) {
        List<Depositos> depositos = depositosRepository.findByAposentadoriaIdAposentadoria(id);

        return depositos.stream()
                .mapToDouble(Depositos::getValor)
                .sum();
    }
}