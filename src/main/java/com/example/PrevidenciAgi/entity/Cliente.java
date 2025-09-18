package com.example.PrevidenciAgi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Campo de relacionamento com a entidade Simulacao
import jakarta.persistence.OneToMany;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String cpf, nome, genero, email, senha;

    // Campo de relacionamento com a entidade Simulacao
    // @OneToMany - Indica que um cliente pode ter várias simulações (relação 1:N)
    // mappedBy = "cliente" - Informa ao JPA que o mapeamento é feito pelo campo "cliente" na classe Simulacao, evitando a criação de uma tabela intermediária
    // cascade = CascadeType.ALL - Propaga operações (salvar, atualizar, deletar) para as simulações associadas. Por exemplo, se um Cliente for deletado, todas as suas simulações também serão.
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Simulacao> simulacoes;
}
