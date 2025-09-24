package com.example.PrevidenciAgi.entity;

import com.example.PrevidenciAgi.enums.TiposDepositos;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Depositos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDeposito;

    private TiposDepositos tipo;

    private double valor;

    @CreationTimestamp
    private LocalDateTime dataDeposito;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idAposentadoria", referencedColumnName = "id")
    private Aposentadoria aposentadoriaCliente;
}