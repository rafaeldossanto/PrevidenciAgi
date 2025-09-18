package com.example.PrevidenciAgi.entity;

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
    private Long idAposentadoria;  // O id não será mostrado no DTO

    @Column(nullable = false)
    private String tipoAposentadoria;   // Ex: progressiva ou regressiva

    @Column(nullable = false)
    private String dataAposentar;       // Data que ele quer se aposentar

    private String dataContratada;      // Data em que contratou o plano

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)  // Relacionamento com Cliente
    private Cliente cliente;  // Cliente que escolheu esse plano de aposentadoria
}

