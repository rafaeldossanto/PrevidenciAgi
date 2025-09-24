package com.example.PrevidenciAgi.entity;

import com.example.PrevidenciAgi.entity.Enum.TipoAposentadoriaEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbAposentadoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Aposentadoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAposentadoria;

    @Enumerated(EnumType.STRING)
    private TipoAposentadoriaEnum tipoAposentadoria;

    @Positive
    private Double valor_mensal;

    @Future
    private LocalDateTime data_aposentar;

    @FutureOrPresent
    private LocalDateTime data_inicio;

    @OneToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id",nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "aposentadoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Depositos> depositos;
}

