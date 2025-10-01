/*
Módulo: Simulacao.java
Descrição: Define a entity simulação e suas propriedades
Autor: Gustavo Henrique dos Anjos
Data: 17/09/2025
*/

package com.example.PrevidenciAgi.model.simulacao;

import com.example.PrevidenciAgi.enums.Genero;
import com.example.PrevidenciAgi.enums.TipoAposentadoria;
import com.example.PrevidenciAgi.model.cliente.Cliente;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate; // Importação adicionada para o LocalDate

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Simulacao")
public class Simulacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSimulacao;

    @NotBlank(message = "O valor mensal e obrigatorio!")
    private Double valorMensal;

    @NotBlank(message = "O valor a receber e obrigatorio!")
    private Double valorReceber;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Enumerated(EnumType.STRING)
    private TipoAposentadoria tipoContribuicao;

    @NotNull
    private LocalDate dataInicial; // Alterado de Integer para LocalDate

    @NotBlank
    private LocalDate dataAposentar; // Alterado de Integer para LocalDate

    @NotBlank(message = "O tempo contribuicao e obrigatorio!")
    private Integer tempoContribuicao;

    @NotBlank(message = "O tempo de recebimento e obrigatorio!")
    private Integer tempoRecebimento;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;
}
