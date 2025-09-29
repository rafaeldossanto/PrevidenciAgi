package com.example.PrevidenciAgi.repository;

import com.example.PrevidenciAgi.entity.Depositos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DepositosRepository extends JpaRepository<Depositos, Long> {

    List<Depositos> findByClienteId(Long id);
    List<Depositos> findByAposentadoriaIdAposentadoria(Long id);

    Double findTotalDepositadoNoMesPorCliente(@Param("id") Long id,
                                                  @Param("inicioMes") LocalDateTime inicioMes,
                                                  @Param("fimMes")LocalDateTime fimMes);
}
