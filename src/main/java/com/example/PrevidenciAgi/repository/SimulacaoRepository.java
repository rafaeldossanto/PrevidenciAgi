package com.example.PrevidenciAgi.repository;

import com.example.PrevidenciAgi.model.simulacao.Simulacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulacaoRepository extends JpaRepository<Simulacao, Long> {
}
