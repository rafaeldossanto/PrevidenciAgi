package com.example.PrevidenciAgi.dto.cliente.request;

public record DadosCadastroRequest(
        String cpf,
        String nome,
        String genero,
        String email,
        String senha
) {
}
