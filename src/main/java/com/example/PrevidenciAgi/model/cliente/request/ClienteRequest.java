package com.example.PrevidenciAgi.model.cliente.request;

import com.example.PrevidenciAgi.enums.Genero;

public record ClienteRequest(
        String cpf,
        String nome,
        Genero genero,
        String email,
        String senha
) {
}
