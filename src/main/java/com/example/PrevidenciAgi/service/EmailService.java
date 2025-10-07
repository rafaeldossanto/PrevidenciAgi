package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.entity.Depositos;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private ClienteRepository clienteRepository;

    @Scheduled(cron = "0 0 8 * * *")
    public void enviarEmailCombranca(){
        var message = new SimpleMailMessage();

        message.setTo();


    }


    public List<Cliente> clientesPendentes() {
        YearMonth mesAtual = YearMonth.now();
        return clienteRepository.findAll().stream()
                .filter(cliente -> {
                    double totalDepositadoNoMes = cliente.getDepositos().stream()
                            .filter(deposito -> {
                                LocalDateTime data = deposito.getDataDeposito();
                                return YearMonth.from(data).equals(mesAtual);
                            })
                            .mapToDouble(Depositos::getValor)
                            .sum();
                    return totalDepositadoNoMes < cliente.getAposentadoria().getValor_mensal();
                })
                .collect(Collectors.toList());
    }
}


