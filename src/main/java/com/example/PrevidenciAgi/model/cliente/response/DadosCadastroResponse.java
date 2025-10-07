package com.example.PrevidenciAgi.model.cliente.response;

public record DadosCadastroResponse(
        Long id,
        String cpf,
        String nome,
        String genero,
        String email,
        String senha
) {
}
