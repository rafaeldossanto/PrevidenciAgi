package com.example.PrevidenciAgi.dto.simulacao;

import com.example.PrevidenciAgi.dto.cliente.Cliente;
import com.example.PrevidenciAgi.Enum.Genero;
import com.example.PrevidenciAgi.Enum.TempoRecebendo;
import com.example.PrevidenciAgi.Enum.TipoSimulacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Simulacao")
public class Simulacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSimulacao;

    @Positive
    private Integer idade;

    @Positive
    private Integer taxaJuros;

    @Enumerated(EnumType.STRING)
    private TipoSimulacao tipoSimulacao;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Positive
    private BigDecimal valorMensal;

    @Positive
    private BigDecimal valorRecebendo;

    @Future
    private LocalDate dataAposentar;

    @Enumerated(EnumType.STRING)
    private TempoRecebendo tempoRecebimento;

    @Positive
    private BigDecimal TotalInvestidoJuros;

    @Positive
    private BigDecimal valorInvestido;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;
}
