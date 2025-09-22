package com.example.PrevidenciAgi.entity;

import com.example.PrevidenciAgi.dto.cliente.request.DadosCadastroRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

// Campo de relacionamento com a entidade Simulacao


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo cpf e obrigatorio!")
    private String cpf;

    @NotBlank(message = "O campo nome e obrigatorio!")
    private String nome;

    @NotBlank(message = "O genero e obrigatorio!")
    private String genero;

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

    public Cliente(DadosCadastroRequest dados) {
        this.cpf = dados.cpf();
        this.nome = dados.nome();
        this.genero = dados.genero();
        this.email = dados.email();
        this.senha = dados.senha();
    }
}
