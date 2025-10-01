package com.example.PrevidenciAgi.repository;

import com.example.PrevidenciAgi.entity.Depositos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DepositosRepository extends JpaRepository<Depositos, Long> {

    List<Depositos> findByClienteId(Long id);
    List<Depositos> findByAposentadoriaIdAposentadoria(Long id);

    Optional<Depositos> findTopByClienteIdOrderByDataDepositoDesc(Long clienteId);

    @Query("SELECT COALESCE(SUM(d.valor), 0) FROM Depositos d WHERE d.cliente.id = :clienteId AND MONTH(d.dataDeposito) = :mes AND YEAR(d.dataDeposito) = :ano")
    Double findTotalDepositadoNoMesPorCliente(@Param("clienteId") Long clienteId,
                                              @Param("mes") int mes,
                                              @Param("ano") int ano);


    @Query("SELECT COALESCE(SUM(d.valor), 0) FROM Depositos d WHERE d.cliente.id = :clienteId AND d.dataDeposito BETWEEN :inicioMes AND :fimMes")
    Double findTotalDepositadoNoPeriodo(@Param("clienteId") Long clienteId,
                                        @Param("inicioMes") LocalDateTime inicioMes,
                                        @Param("fimMes") LocalDateTime fimMes);
}
