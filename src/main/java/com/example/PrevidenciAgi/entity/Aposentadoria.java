package com.example.PrevidenciAgi.entity;

import com.example.PrevidenciAgi.entity.Enum.TipoAposentadoria;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Aposentadoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAposentadoria;

    @Enumerated(EnumType.STRING)
    private TipoAposentadoria tipoAposentadoria;

    @Positive(message = "O valor mensal deve ser positivo")
    private Double valor_mensal;

    @PositiveOrZero(message = "valor do deposito tem que ser maior que zero.")
    private Double valor_deposito;

    @FutureOrPresent(message = "A data deve ser atual ou futura.")
    private LocalDate data_contratada;

    @Future(message = "A data pra se aposentar tem que ser futura.")
    private LocalDate data_aposentar;

    @FutureOrPresent(message = "A data deve ser agora ou futura.")
    private LocalDate data_inicio;

    @OneToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id",nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "aposentadoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Depositos> depositos;
}

