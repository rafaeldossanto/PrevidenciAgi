package com.example.PrevidenciAgi.repository;

import com.example.PrevidenciAgi.entity.Depositos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositosRepository extends JpaRepository<Depositos, Long> {

    List<Depositos> findByClienteId(Long id);
    List<Depositos> findByAposentadoriaIdAposentadoria(Long id);
}
