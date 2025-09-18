package com.example.PrevidenciAgi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data // Adicionado para gerar getters e setters
public class SimulacaoDto {

    @NotNull(message = "O valor mensal não pode ser nulo.")
    @Positive(message = "O valor mensal deve ser um número positivo.")
    private Double valorMensal; // Alterado para Double para ser compatível com @NotNull

    @NotNull(message = "A data inicial não pode ser nula.")
    private LocalDateTime dataInicial;

    @NotNull(message = "A data de aposentadoria não pode ser nula.")
    private LocalDateTime dataAposentar;
}
