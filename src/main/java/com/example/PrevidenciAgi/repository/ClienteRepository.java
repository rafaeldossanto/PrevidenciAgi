package com.example.PrevidenciAgi.repository;

import com.example.PrevidenciAgi.dto.ClienteDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClienteRepository extends JpaRepository<ClienteDto, Long> {
    boolean existsByCpf(String cpf);
}
