package com.example.PrevidenciAgi.model.cliente;

import com.example.PrevidenciAgi.enums.Genero;
import com.example.PrevidenciAgi.model.aposentadoria.Aposentadoria;
import com.example.PrevidenciAgi.model.cliente.request.ClienteRequest;
import com.example.PrevidenciAgi.model.deposito.Depositos;
import com.example.PrevidenciAgi.model.simulacao.Simulacao;
import jakarta.persistence.*;
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
    private Genero genero;

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

    public Cliente(ClienteRequest dados) {
        this.cpf = dados.cpf();
        this.nome = dados.nome();
        this.genero = dados.genero();
        this.email = dados.email();
        this.senha = dados.senha();
    }
}
