package com.example.PrevidenciAgi.model.cliente.request;

public record DadosCadastroRequest(
        String cpf,
        String nome,
        String genero,
        String email
) {
}