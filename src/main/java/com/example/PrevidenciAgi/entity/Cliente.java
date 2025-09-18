package com.example.PrevidenciAgi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// Campo de relacionamento com a entidade Simulacao


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String cpf, nome, genero, email, senha;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Simulacao> simulacoes;
    private List<Depositos> depositos;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "idAposentadoria")
    private Aposentadoria aposentadoria;
}
