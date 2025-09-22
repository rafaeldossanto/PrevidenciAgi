package com.example.PrevidenciAgi.dto.cliente.request;

public record DadosCadastroCliente(
        String cpf,
        String nome,
        String genero,
        String email,
        String senha
) {
}
