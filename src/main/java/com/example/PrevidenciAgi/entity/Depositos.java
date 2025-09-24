package com.example.PrevidenciAgi.entity;

import com.example.PrevidenciAgi.enums.TiposDepositos;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Depositos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDeposito;

    @NotBlank(message = "O tipo e obrigatorio!")
    private TiposDepositos tipo;

    @NotBlank(message = "O valor e obrigatorio!")
    private double valor;

    @NotNull
    private LocalDateTime dataDeposito;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idCliente", referencedColumnName = "id")
    private Cliente cliente;
}