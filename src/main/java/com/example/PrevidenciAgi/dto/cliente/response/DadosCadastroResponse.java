package com.example.PrevidenciAgi.dto.cliente.response;

import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.enums.Genero;

public record DadosCadastroResponse(
        Long id,
        String cpf,
        String nome,
        Genero genero,
        String email,
        String senha
) {
    public DadosCadastroResponse(Cliente cliente) {
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
