package com.example.PrevidenciAgi.dto.cliente.request;

import com.example.PrevidenciAgi.entity.Aposentadoria;
import com.example.PrevidenciAgi.entity.Enum.Genero;

public record DadosRequest(
        String cpf,
        String nome,
        Genero genero,
        String email,
        String senha,
        Aposentadoria aposentadoria
) {
}
