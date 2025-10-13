package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import com.example.PrevidenciAgi.service.exception.NaoEncontrado;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;
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

    @Transactional
    @Scheduled(fixedRate = 259200000)
    public void enviarEmailCombranca() {
        try {
            String html = carregarHtml("templates/email.html");
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("contaagiemail@gmail.com");
            helper.setTo(emailsPendentes());
            helper.setSubject("Lembrete de Depósito");
            helper.setText(html, true);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String carregarHtml(String caminho) throws Exception {
        var resource = new ClassPathResource(caminho);
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    public void recuperacaoSenha(String email) {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new NaoEncontrado("Cliente com essa conta nao encontrada."));

        var message = new SimpleMailMessage();

        message.setFrom("contaagiemail@gmail.com");
        message.setTo(cliente.getEmail());
        message.setSubject("Recuperação de senha");
        message.setText("Sua senha é essa: " + cliente.getSenha());
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


