package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private DepositosService depositosService;

    //fazer test para ver se o envio de email esta funcionando
    //criar um email para servir de email de envio para as contas cadastradas
    //...

    @Scheduled(cron = "0 0 8 * * *")
    public void enviarEmailCombranca() {
        var message = new SimpleMailMessage();

        message.setFrom("rafael.santoss2802@gmail.com");
        message.setTo(emailsPendentes());
        message.setSubject("Lembrete de Depósito");
        message.setText("Olá! Você ainda não realizou seu depósito este mês. Por favor, regularize sua situação.");
        mailSender.send(message);
    }

    private List<Cliente> clientesPendentes() {
        return clienteRepository.findAll().stream()
                .filter(cliente -> depositosService.totalDepositadoMes(cliente.getId()) == 0.0)
                .collect(Collectors.toList());
    }

    private String[] emailsPendentes() {
        return clientesPendentes().stream()
                .map(Cliente::getEmail)
                .toArray(String[]::new);
    }
}


