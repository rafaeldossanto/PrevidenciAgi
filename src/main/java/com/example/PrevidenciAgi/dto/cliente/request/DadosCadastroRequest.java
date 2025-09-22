package com.example.PrevidenciAgi.dto.cliente.request;

import com.example.PrevidenciAgi.entity.Cliente;

public record DadosCadastroRequest(
        String cpf,
        String nome,
        String genero,
        String email,
        String senha
) {
}
