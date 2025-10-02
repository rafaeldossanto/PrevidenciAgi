package com.example.PrevidenciAgi.dto.cliente.request;

import com.example.PrevidenciAgi.entity.Enum.AtualizacaoDeDados;

public record AtualizarDadosRequest(
        AtualizacaoDeDados dado,
        String mudanca
) {
}
