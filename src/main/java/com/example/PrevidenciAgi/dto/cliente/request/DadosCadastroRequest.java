package com.example.PrevidenciAgi.dto.cliente.request;

import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.enums.Genero;

public record DadosCadastroRequest(
        String cpf,
        String nome,
        Genero genero,
        String email,
        String senha
) {
}
