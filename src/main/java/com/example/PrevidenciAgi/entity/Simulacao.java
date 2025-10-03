package com.example.PrevidenciAgi.entity;

// Imports omitidos para brevidade...
import com.example.PrevidenciAgi.entity.Enum.Genero;
import com.example.PrevidenciAgi.entity.Enum.TipoSimulacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "simulacao")
public class Simulacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSimulacao;

    // --- CAMPOS DE ENTRADA (Obrigatórios) ---

    @Column(nullable = false)
    @Positive
    private Integer idade;

    @Column(nullable = false)
    @Positive
    private Integer taxaJuros;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSimulacao tipoSimulacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) // Assumindo Gênero é obrigatório
    private Genero genero;

    @Column(nullable = false)
    private LocalDate dataInicial; // AGORA OBRIGATÓRIO NA ENTITY

    @Column(nullable = false)
    private LocalDate dataAposentar;

    // Tempo salvo em meses para cálculos robustos
    @Column(nullable = false)
    private Integer tempoContribuicaoEmMeses; // Substitui tempoContribuicao: String

    @Column(nullable = false)
    private Integer tempoRecebimentoEmMeses; // Substitui tempoRecebimento: String

    // --- CAMPOS DE ENTRADA/SAÍDA (Valores Monetários) ---

    // Este campo pode ser o aporte (se DEPOSITAR) ou a renda desejada (se RECEBER)
    @Column(precision = 19, scale = 2)
    private BigDecimal valorInputMensal;

    // Resultado Calculado: Aporte mensal efetivo (calculado ou fornecido)
    @Column(precision = 19, scale = 2)
    private BigDecimal valorMensal;

    // Resultado Calculado: Renda mensal a ser recebida (calculada ou fornecida)
    @Column(precision = 19, scale = 2)
    private BigDecimal valorRecebendo;

    // Resultado Calculado: Total de dinheiro depositado (sem juros)
    @Column(precision = 19, scale = 2)
    private BigDecimal valorInvestido;

    // Resultado Calculado: Saldo final (com juros)
    @Column(precision = 19, scale = 2)
    private BigDecimal totalInvestidoJuros;

    // --- RELACIONAMENTO ---

    @ManyToOne(fetch = FetchType.LAZY) // Usar LAZY para performance
    @JoinColumn(name = "idCliente")
    private Cliente cliente;
}