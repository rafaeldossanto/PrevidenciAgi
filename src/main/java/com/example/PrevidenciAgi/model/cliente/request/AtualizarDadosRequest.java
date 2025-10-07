package com.example.PrevidenciAgi.model.cliente.request;

import com.example.PrevidenciAgi.Enum.AtualizacaoDeDados;

public record AtualizarDadosRequest(
        AtualizacaoDeDados dado,
        String mudanca
) {
}
