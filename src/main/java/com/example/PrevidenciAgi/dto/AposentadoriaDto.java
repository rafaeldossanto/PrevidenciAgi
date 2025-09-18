package com.example.PrevidenciAgi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AposentadoriaDto {

    @NotNull(message = "Tipo de aposentadoria é obrigatório")
    private String tipoAposentadoria;  // Progressiva e Regressiva

    @NotNull(message = "Data para aposentadoria é obrigatória")
    private String dataAposentar;  //

    @NotNull(message = "Data de contratação é obrigatória")
    private String dataContratada; //

    @NotNull(message = "Cliente ID é obrigatório")
    private Long clienteId;  // Referência ao cliente que está fazendo a aposentadoria
}


