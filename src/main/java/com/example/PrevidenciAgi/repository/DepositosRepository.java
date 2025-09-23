package com.example.PrevidenciAgi.repository;

import com.example.PrevidenciAgi.entity.Aposentadoria;
import com.example.PrevidenciAgi.entity.Cliente;
import com.example.PrevidenciAgi.entity.Depositos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositosRepository extends JpaRepository<Depositos, Long> {
    List<Depositos> findByAposentadoria(Aposentadoria aposentadoriaCliente);

    @Query("SELECT SUM(d.valor) FROM Depositos d " +
           "WHERE d.cliente.id = :clienteId " +
            "AND MONTH(d.dataDeposito) = :mes " +
            "AND YEAR(d.dataDeposito) = :ano ")
    Double somarDepositosDoMes(@Param("clienteId") Long clienteId,
                               @Param("mes") int mes,
                               @Param("ano") int ano);
}
