package com.example.PrevidenciAgi.model.cliente;

import com.example.PrevidenciAgi.model.aposentadoria.Aposentadoria;
import com.example.PrevidenciAgi.model.deposito.Depositos;
import com.example.PrevidenciAgi.Enum.Genero;
import com.example.PrevidenciAgi.Enum.Role;
import com.example.PrevidenciAgi.model.simulacao.Simulacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


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

    @Enumerated(EnumType.STRING)
    private Role role;

    @Email
    @NotBlank(message = "O email e obrigatorio!, e deve ser do formato correto.")
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
