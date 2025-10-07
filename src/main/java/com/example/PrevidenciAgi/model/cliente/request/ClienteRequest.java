package com.example.PrevidenciAgi.model.cliente.request;

public record ClienteRequest(
        String cpf,
        String nome,
        String genero,
        String email
) {
}