package com.example.PrevidenciAgi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive; // Adicionado para campos Double/Integer
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    // --- Campos Numéricos e de Data (Usam @NotNull ou @Positive) ---

    // NOTA: Entidades JPA tipicamente não devem ter validação de input (@NotNull, @Positive),
    // pois isso é função do DTO. Mantenho aqui por fins de mapeamento, mas o DTO é o local correto.
    @NotNull(message = "O valor mensal é obrigatório.")
    @Positive(message = "O valor mensal deve ser positivo.")
    private Double valorMensal;

    @NotNull(message = "O valor a receber é obrigatório.")
    private Double valorReceber;

    @NotNull(message = "A data inicial é obrigatória.")
    private LocalDate dataInicial;

    @NotNull(message = "A data de aposentadoria é obrigatória.")
    private LocalDate dataAposentar;

    @NotNull(message = "O tempo de contribuição é obrigatório.")
    @Positive(message = "O tempo de contribuição deve ser positivo.")
    private Integer tempoContribuicao;

    @NotNull(message = "O tempo de recebimento é obrigatório.")
    @Positive(message = "O tempo de recebimento deve ser positivo.")
    private Integer tempoRecebimento;

    // --- Campos de String (Usam @NotBlank) ---

    @NotBlank(message = "O gênero é obrigatório.")
    private String genero;

    @NotBlank(message = "O tipo de contribuição é obrigatório.")
    private String tipoContribuicao; // Ex: PGBL, VGBL

    // --- Relacionamento ---

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;
}