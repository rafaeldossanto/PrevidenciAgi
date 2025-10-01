package com.example.PrevidenciAgi.repository;

import com.example.PrevidenciAgi.entity.Aposentadoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AposentadoriaRepository extends JpaRepository<Aposentadoria, Long> {
    boolean existsByClienteId(Long id);

}
