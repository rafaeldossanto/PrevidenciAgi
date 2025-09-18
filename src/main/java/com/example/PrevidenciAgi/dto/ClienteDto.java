package com.example.PrevidenciAgi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClienteDto {
    @NotNull(message = "CPF é obrigatório")
    private String cpf;

    @NotNull(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Gênero é obrigatório")
    private String genero;

    @NotNull(message = "Email é obrigatório")
    private String email;

    @NotNull(message = "Senha é obrigatório")
    private String senha;
}
