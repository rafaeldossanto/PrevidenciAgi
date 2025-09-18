package com.example.PrevidenciAgi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data //(Lombok) -> gera getters, setters, toString, equals e hashCode
@NoArgsConstructor // (Lombok) -> gera construtor
@AllArgsConstructor // (Lombok) -> gera construtor
@Table(name="Simulação") // nome da tabela no banco
public class Simulacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incremento
    private Long id;
    private String descricao;
    private String responsavel;
    private String objetivo;
    private String resultado; // Resultado

    //Construtor vazio (obrigatório para o JPA)
}
