package com.example.PrevidenciAgi.model.cliente.response;

import com.example.PrevidenciAgi.model.cliente.Cliente;
import com.example.PrevidenciAgi.enums.Genero;

public record ClienteResponse(
        Long id,
        String cpf,
        String nome,
        Genero genero,
        String email,
        String senha
) {
    public ClienteResponse(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getCpf(),
                cliente.getNome(),
                cliente.getGenero(),
                cliente.getEmail(),
                cliente.getSenha()
        );
    }
}
