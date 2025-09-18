package com.example.PrevidenciAgi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Simulacao")
public class Simulacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSimulacao;

    private Double valorMensal;
    private Double valorReceber;
    private String genero;
    private String tipoContribuicao;
    private Integer dataInicial;
    private Integer dataAposentar;
    private Integer tempoContribuicao;
    private Integer tempoRecebimento;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;
}
