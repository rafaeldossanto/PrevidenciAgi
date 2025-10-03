package com.example.PrevidenciAgi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "Contrato_Previdencia")
public class ContratoPrevidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Dados do Contrato
    private BigDecimal valorMensal;
    private String tipoPlano; // PGBL ou VGBL
    private String regimeTributario; // Progressiva ou Regressiva
    private LocalDate dataInicioContrato; // Data da assinatura

    // Projeção de Aposentadoria (Resultado da Simulação)
    private LocalDate dataAposentadoria;
    private Double valorBeneficioEsperado;

    // Status e Relacionamento
    private String status; // Ex: "ASSINADO", "EM_BENEFICIO", "CANCELADO"

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;
}