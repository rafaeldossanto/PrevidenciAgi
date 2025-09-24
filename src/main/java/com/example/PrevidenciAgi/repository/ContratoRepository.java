package com.example.PrevidenciAgi.repository;

import com.example.PrevidenciAgi.entity.ContratoPrevidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratoRepository extends JpaRepository<ContratoPrevidencia, Long> {
    // Métodos personalizados podem ser adicionados aqui, se necessário.
}