package com.example.PrevidenciAgi.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class SimulacaoDto {
    @NotNull(message = "Valor mensal é obrigatório!")
    private double valorMensal;

    @NotNull(message = "Data Inicial é obrigatória!")
    private LocalDateTime dataInicial;

    @NotNull(message = "Data aposentadoria é obrigatório!")
    private LocalDateTime dataAposentar;
}
