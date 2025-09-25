package com.example.PrevidenciAgi.repository;

import com.example.PrevidenciAgi.entity.Aposentadoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AposentadoriaRepository extends JpaRepository<Aposentadoria, Long> {
    boolean existsByClienteId(Long id);

    @Query("SELECT a FROM Aposentadoria a JOIN FETCH a.cliente WHERE a.idAposentadoria = :id")
    Optional<Aposentadoria> findByIdWithCliente(@Param("id") Long id);
}


