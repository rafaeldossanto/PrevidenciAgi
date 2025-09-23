package com.example.PrevidenciAgi.entity;

import com.example.PrevidenciAgi.dto.aposentadoria.request.AposentadoriaRequest;
import com.example.PrevidenciAgi.enums.TipoAposentadoria;
import jakarta.persistence.*;
import jakarta.validation.Valid;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAposentadoria tipoAposentadoria;   // Ex: progressiva ou regressiva

    @NotBlank(message = "A data de aposentadoria e obrigatorio!")
    @Column(nullable = false)
    private String dataAposentar;       // Data que ele quer se aposentar

    @NotBlank(message = "A data contratada e obrigatoria!")
    private String dataContratada;      // Data em que contratou o plano

    @OneToOne
    @JoinColumn(name = "idCliente", nullable = false)  // Relacionamento com Cliente
    private Cliente cliente;  // Cliente que escolheu esse plano de aposentadoria


    public Aposentadoria(@Valid AposentadoriaRequest dados) {
        this.tipoAposentadoria = dados.tipoAposentadoria();
        this.dataAposentar = dados.dataAposentar();
        this.dataContratada = dados.dataContratada();
    }
}

