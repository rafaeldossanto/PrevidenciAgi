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
import com.example.PrevidenciAgi.service.exception.TempoInsuficiente;
import com.example.PrevidenciAgi.service.exception.ValorInvalido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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

        Double novoSaldo = aposentadoria.getSaldo() + request.valor();

        Double totalDepositadoMes = totalDepositadoMes(cliente.getId());

        Double totalAposDeposito = totalDepositadoMes + request.valor();

        Depositos deposito = new Depositos();
        deposito.setDataDeposito(agora);
        deposito.setValor(request.valor());
        deposito.setAposentadoria(aposentadoria);
        deposito.setCliente(cliente);
        aposentadoria.setSaldo(novoSaldo);

        if (totalAposDeposito > aposentadoria.getValor_mensal()) {
            deposito.setTipo(TipoDeposito.APORTE);
        } else {
            deposito.setTipo(TipoDeposito.MENSAL);
        }

        return depositosRepository.save(deposito);
    }

    public Double saqueAdiantado(Long id, Double valor){
        Aposentadoria aposentadoria = aposentadoriaRepository.findById(id)
                .orElseThrow(() -> new NaoEncontrado("Aposentadoria nao encontrada"));

        Double valorImposto = valor + valor*0.25;

        if (aposentadoria.getSaldo() < valorImposto) {
            throw new ValorInvalido("Saldo insuficiente");
        }
        if (aposentadoria.getData_inicio().getYear() >= LocalDate.now().getYear() - 2) {
            throw new TempoInsuficiente("O tempo para fazer o emprestimo ainda nao e suficiente");
        }

        aposentadoria.setSaldo(aposentadoria.getSaldo() - valorImposto);
        aposentadoriaRepository.save(aposentadoria);

        return valor;
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

    public Double totalDepositadoMes(Long id){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NaoEncontrado("Cliente com esse Id nao encontrado."));
        int mesAtual = LocalDate.now().getMonthValue();

        return cliente.getDepositos()
                .stream()
                .filter(n -> n.getDataDeposito().getMonthValue() == mesAtual)
                .mapToDouble(Depositos::getValor)
                .sum();
    }
}