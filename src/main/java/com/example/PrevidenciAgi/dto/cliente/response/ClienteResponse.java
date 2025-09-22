package com.example.PrevidenciAgi.dto.cliente.response;

public record ClienteResponse(
        Long id,
        String cpf,
        String nome,
        String genero,
        String email,
        String senha
) {
}
