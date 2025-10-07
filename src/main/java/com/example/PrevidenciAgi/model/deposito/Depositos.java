package com.example.PrevidenciAgi.model.deposito;

import com.example.PrevidenciAgi.model.aposentadoria.Aposentadoria;
import com.example.PrevidenciAgi.model.cliente.Cliente;
import com.example.PrevidenciAgi.Enum.TipoDeposito;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Depositos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDeposito;

    @Enumerated(EnumType.STRING)
    private TipoDeposito tipo;

    @Positive(message = "O valor tem que ser maior que zero.")
    private Double valor;

    @PastOrPresent(message = "A data deve ser no dia atual.")
    private LocalDateTime dataDeposito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aposentadoria", referencedColumnName = "idAposentadoria")
    private Aposentadoria aposentadoria;
}