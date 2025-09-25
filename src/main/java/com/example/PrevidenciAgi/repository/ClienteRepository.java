package com.example.PrevidenciAgi.repository;

import com.example.PrevidenciAgi.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByCpf(String cpf);
    Optional<Cliente> findByEmail(String email);
}
