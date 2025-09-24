package com.example.PrevidenciAgi.service.DepositosService;


import com.example.PrevidenciAgi.dto.deposito.request.DepositosRequest;
import com.example.PrevidenciAgi.dto.deposito.response.DepositosResponse;
import com.example.PrevidenciAgi.entity.Aposentadoria;
import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.entity.Depositos;
import com.example.PrevidenciAgi.enums.TiposDepositos;
import com.example.PrevidenciAgi.repository.AposentadoriaRepository;
import com.example.PrevidenciAgi.repository.DepositosRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DepositosService {

    @Autowired
    private AposentadoriaRepository aposentadoriaRepository;

    @Autowired
    private DepositosRepository depositosRepository;

    public DepositosRequest depositar(Long idAposentadoria, DepositosRequest request){
        Aposentadoria aposentadoriaCliente = aposentadoriaRepository.findById(idAposentadoria)
                .orElseThrow(() -> new EntityExistsException("Cliente com esse Id nao encontrado."));

        Depositos deposito = new Depositos();
        deposito.setAposentadoriaCliente(aposentadoriaCliente);
        deposito.setValor(request.valor());

        depositosRepository.save(deposito);

        return new DepositosRequest(deposito.getValor());
    }

    public List<DepositosResponse> listarDepositos(Long idAposentadoria){
        Aposentadoria aposentadoriaCliente = aposentadoriaRepository.findById(idAposentadoria)
                .orElseThrow(() -> new EntityExistsException("Cliente com esse Id nao encontrado."));

        return depositosRepository.findByAposentadoria(aposentadoriaCliente).stream()
                .map(deposito -> new DepositosResponse(
                        deposito.getIdDeposito(),
                        deposito.getTipo(),
                        deposito.getValor(),
                        deposito.getDataDeposito()
                ))
                .toList();
    }

    public Depositos listarUnico (Long idAposentadoria, Long depositoId){
        Aposentadoria aposentadoriaCliente = aposentadoriaRepository.findById(idAposentadoria)
                .orElseThrow(() -> new EntityExistsException("Cliente com esse Id nao encontrado."));

        Depositos deposito = depositosRepository.findById(depositoId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com esse Id nao encontrado."));

        if (!deposito.getAposentadoriaCliente().getIdAposentadoria().equals(aposentadoriaCliente.getIdAposentadoria())) {
            throw new IllegalArgumentException("Esse depósito não pertence ao cliente informado.");
        }

        return deposito;
    }

    //Metodo ainda nao testado, consiste no deposito ja automatico se for aporte ou mensal,
    // com a variavel de VALORESCOLHIDO na tabela de aposentadoria.
    //Metodo ainda nao atribuido no controller.
    public Depositos realizarDeposito(Long idAposentadoria, double valor){
        Aposentadoria aposentadoriaCliente = aposentadoriaRepository.findById(idAposentadoria)
                .orElseThrow(() -> new EntityExistsException("Cliente com esse Id nao encontrado."));

        LocalDateTime agora = LocalDateTime.now();
        int mes = agora.getMonthValue();
        int ano = agora.getYear();

        Double somaMensal = depositosRepository.somarDepositosDoMes(idAposentadoria, mes, ano);
        if(somaMensal == null) somaMensal = 0.0;

        double totalComNovo = somaMensal + valor;

        TiposDepositos tipo;
        if (somaMensal >= aposentadoriaCliente.getValorMensalG()){
            tipo = TiposDepositos.APORTE;
        }else if (totalComNovo <= aposentadoriaCliente.getValorMensalG()){
            tipo = TiposDepositos.MENSAL;
        }else {
            tipo = TiposDepositos.APORTE;
        }

        Depositos deposito = new Depositos();
        deposito.setAposentadoriaCliente(aposentadoriaCliente);
        deposito.setValor(valor);
        deposito.setTipo(tipo);
        deposito.setDataDeposito(agora);

        return depositosRepository.save(deposito);
    }

    /*mas eu quero fazer o seguinte, vamos supor que um cliente estabeleça que ele vai depositar 200 reais por mes,
        e por algum a caso ele so fez depositos de 50 reais no mes e realizou 6 depositos em um unico mes,
        eu quero pegar esses depositos e 4 deles serao considerados mensais e os outros 2 seriao aporte, entendeu ?*/


}
