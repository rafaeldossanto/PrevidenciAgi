package com.example.PrevidenciAgi.dto.cliente.request;

public record ClienteRequest(
        String cpf,
        String nome,
        String genero,
        String email,
        String senha
) {
}
