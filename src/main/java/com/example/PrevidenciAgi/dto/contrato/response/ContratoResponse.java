// package com.example.PrevidenciAgi.dto.contrato.response;

import java.time.LocalDate;

public record ContratoResponse(
        Long idContrato,
        String tipoPlano,            // Ex: PGBL, VGBL
        String regimeTributario,     // Ex: Progressiva, Regressiva
        Double valorMensal,
        Double valorBeneficioEsperado, // Valor projetado na simulação
        LocalDate dataAposentadoria,
        LocalDate dataAssinatura,
        String statusContrato         // Ex: ASSINADO
) {
}