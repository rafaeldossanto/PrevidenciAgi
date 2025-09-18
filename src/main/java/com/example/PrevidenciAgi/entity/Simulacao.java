package com.example.PrevidenciAgi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // (Lombok) - Gera automaticamente os getters, setters, toString, equals e hashCode
@NoArgsConstructor // (Lombok) - Cria um construtor sem argumentos, obrigatório pelo JPA
@AllArgsConstructor // (Lombok) - Cria um construtor com todos os argumentos
@Table(name="Simulacao") // Define o nome da tabela no banco de dados
public class Simulacao {

    @Id // Marca este campo como a chave primária da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura o banco de dados para gerar automaticamente o ID (auto-incremento)
    private Long id;

    // Atributos da simulação
    private String descricao;
    private String objetivo;
    private String resultado; // Armazena o resultado da simulação

    // Campo de relacionamento com a entidade Cliente
    // @ManyToOne indica que muitas simulações podem pertencer a um único cliente (relação N:1)
    @ManyToOne
    // @JoinColumn(name = "cliente_id") - Cria uma coluna na tabela "simulacao" chamada "cliente_id", que será a chave estrangeira (foreign key)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
