package com.example.PrevidenciAgi.repository;

import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.entity.Depositos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositosRepository extends JpaRepository<Depositos, Long> {
    List<Depositos> findByCliente(Cliente cliente);
}
