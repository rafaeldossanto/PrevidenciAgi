package com.example.PrevidenciAgi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "O tipo de aposentadoria e obrigatorio!")
    @Column(nullable = false)
    private String tipoAposentadoria;   // Ex: progressiva ou regressiva

    @NotBlank(message = "A data de aposentadoria e obrigatorio!")
    @Column(nullable = false)
    private String dataAposentar;       // Data que ele quer se aposentar

    @NotBlank(message = "A data contratada e obrigatoria!")
    private String dataContratada;      // Data em que contratou o plano

    @OneToOne
    @JoinColumn(name = "idCliente", nullable = false)  // Relacionamento com Cliente
    private Cliente cliente;  // Cliente que escolheu esse plano de aposentadoria
}

