package com.example.PrevidenciAgi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDateTime; // Ou String, dependendo do que o front envia. Vamos assumir um padrão de data.

@Data
public class SimulacaoDto {

    @NotNull(message = "O valor mensal não pode ser nulo.")
    @Positive(message = "O valor mensal deve ser um número positivo.")
    private Double valorMensal;

    @NotNull(message = "O gênero não pode ser nulo.")
    private String genero;

    @NotNull(message = "O tipo de contribuicao não pode ser nulo.")
    private String tipoContribuicao;

    @NotNull(message = "A data inicial não pode ser nula.")
    private Integer dataInicial; // Alterado para Integer, para corresponder à entidade

    @NotNull(message = "A data de aposentadoria não pode ser nula.")
    private Integer dataAposentar; // Alterado para Integer, para corresponder à entidade

    @NotNull(message = "O tempo de contribuicao não pode ser nulo.")
    @Positive(message = "O tempo de contribuicao deve ser um numero positivo.")
    private Integer tempoContribuicao;
    
    @NotNull(message = "O tempo de recebimento não pode ser nulo.")
    @Positive(message = "O tempo de recebimento deve ser um numero positivo.")
    private Integer tempoRecebimento;

    @NotNull(message = "O valor a receber não pode ser nulo.")
    @Positive(message = "O valor a receber deve ser um numero positivo.")
    private Double valorReceber;

    @NotNull(message = "O ID do cliente não pode ser nulo.")
    @Positive(message = "O ID do cliente deve ser um numero positivo.")
    private Long idCliente; // Campo para o ID do cliente relacionado
}
