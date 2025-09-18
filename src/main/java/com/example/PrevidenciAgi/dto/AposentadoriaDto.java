package com.example.PrevidenciAgi.dto;

import lombok.Data;

@Data
public class AposentadoriaDto {
    private Long id;
    private String tipoAposentadoria;
    private String dataAposentar;
    private String dataContratada;
    private Long clienteId; // referência ao cliente dono da aposentadoria
}

