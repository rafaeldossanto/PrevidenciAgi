package com.example.PrevidenciAgi.service;

import com.example.PrevidenciAgi.entity.Cliente;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailCobrancaParaTodos(List<Cliente> clientes, BigDecimal valorPendente) {
        for (Cliente cliente : clientes) {
            enviarEmailCobrancaSimples(cliente, valorPendente);
        }
    }

    public void enviarEmailCobrancaSimples(Cliente cliente, BigDecimal valorPendente) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(cliente.getEmail());
            helper.setSubject("Cobrança Pendente - Mensalidade Atrasada");
            helper.setFrom("noreply@empresa.com");

            String textoEmail = criarConteudoEmail(cliente, valorPendente);
            helper.setText(textoEmail, true);

            mailSender.send(message);
            System.out.println("📧 Email enviado para: " + cliente.getEmail());

        } catch (Exception e) {
            System.err.println("❌ Erro ao enviar email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String criarConteudoEmail(Cliente cliente, BigDecimal valorPendente) {
        return String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <style>
                        body { font-family: Arial, sans-serif; padding: 20px; }
                        .container { max-width: 600px; margin: 0 auto; }
                        .valor { color: #dc3545; font-size: 20px; font-weight: bold; }
                        .header { background: #f8f9fa; padding: 15px; border-radius: 5px; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h2>💳 Cobrança Pendente</h2>
                        </div>
                        <p>Prezado(a) <strong>%s</strong>,</p>
                        <p>Identificamos que sua mensalidade no valor de
                           <span class="valor">R$ %s</span>
                           encontra-se pendente.</p>
                        <p><strong>Data de vencimento:</strong> %s</p>
                        <p>Para evitar suspensão do serviço, solicitamos que regularize
                           seu pagamento o mais breve possível.</p>
                        <br>
                        <p>Atenciosamente,<br>Equipe Financeira</p>
                    </div>
                </body>
                </html>
                """, cliente.getNome(), valorPendente, LocalDate.now().plusDays(5));
    }


//    @Scheduled(cron = "0 0 8 * * *")
//    public void enviarCobrancasAutomaticamente() {
//        List<Cliente> inadimplentes = clienteService.buscarInadimplentes();
//        Double valorPendente = ;
//        enviarEmailCobrancaParaTodos(inadimplentes, valorPendente);
//    }
}


