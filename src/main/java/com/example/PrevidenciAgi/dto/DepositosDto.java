package com.example.PrevidenciAgi.dto;

import com.example.PrevidenciAgi.entity.Cliente;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
    public class DepositosDto {

        @NotNull(message = "Id de deposito é obrigatório")
        private Long idDeposito;

        @NotNull(message = "tipo de deposito é obrigatório")
        private String tipo;

        @NotNull(message = "data é obrigatório")
        private LocalDateTime dataDeposito;

        @NotNull(message = "Id do cliente é obrigatorio")
        private Cliente cliente;

}

