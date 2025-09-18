package com.example.PrevidenciAgi.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbAposentadoria")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aposentadoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAposentadoria;

    @Column(nullable = false)
    private String tipoAposentadoria;   //progressiva ou regressiva

    @Column(nullable = false)
    private String dataAposentar;       // Data que ele quer se aposentar

    private String dataContratada;      // Data em que contratou o plano

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente; // ja que acada aposentadoria pertence a um cliente, coloquei cliente aqui
}
