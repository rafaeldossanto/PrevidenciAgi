package com.example.PrevidenciAgi.entity;

import com.example.PrevidenciAgi.entity.Enum.TipoAposentadoriaEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private TipoAposentadoriaEnum tipo;

    @Positive
    private Double valor;

    @PastOrPresent
    private LocalDateTime dataDeposito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aposentadoria")
    private Aposentadoria aposentadoria;
}