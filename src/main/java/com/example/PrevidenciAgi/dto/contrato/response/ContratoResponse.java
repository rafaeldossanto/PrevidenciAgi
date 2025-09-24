package com.example.PrevidenciAgi.dto.contrato.response;

import java.time.LocalDate;

public record ContratoResponse(
        Long idContrato,
        String tipoPlano,
        String regimeTributario,
        Double valorMensal,
        Double valorBeneficioEsperado,
        LocalDate dataAposentadoria,
        LocalDate dataAssinatura,
        String statusContrato
) {
}