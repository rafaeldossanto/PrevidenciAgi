package com.example.PrevidenciAgi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data //(Lombok) -> gera getters, setters, toString, equals e hashCode
@NoArgsConstructor // (Lombok) -> gera construtor
@AllArgsConstructor // (Lombok) -> gera construtor
public class Simulacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incremento
    private Long idSimulacao;

    private double valorMensal;
    private LocalDateTime dataInicial;
    private LocalDateTime dataAposentar;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;

}
