package com.example.PrevidenciAgi.entity;

import com.example.PrevidenciAgi.entity.Enum.GeneroEnum;
import com.example.PrevidenciAgi.entity.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "O campo cpf e obrigatorio!")
    private String cpf;

    @NotBlank(message = "O campo nome e obrigatorio!")
    private String nome;

    @Enumerated(EnumType.STRING)
    private GeneroEnum genero;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Email
    @NotBlank(message = "O email e obrigatorio!")
    private String email;

    @NotBlank(message = "A senha e obrigatoria!")
    private String senha;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Simulacao> simulacoes;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Depositos> depositos;

    @OneToOne
    @JoinColumn(name = "idAposentadoria")
    private Aposentadoria aposentadoria;
}
