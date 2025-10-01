package com.example.PrevidenciAgi.serviceTest;

import com.example.PrevidenciAgi.dto.deposito.request.DepositosRequest;
import com.example.PrevidenciAgi.model.aposentadoria.Aposentadoria;
import com.example.PrevidenciAgi.model.cliente.Cliente;
import com.example.PrevidenciAgi.model.deposito.Depositos;
import com.example.PrevidenciAgi.enums.TipoDeposito;
import com.example.PrevidenciAgi.repository.AposentadoriaRepository;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import com.example.PrevidenciAgi.repository.DepositosRepository;
import com.example.PrevidenciAgi.service.DepositosService;
import com.example.PrevidenciAgi.exception.NaoEncontrado;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DepositosServiceTest {

    @Mock
    private DepositosRepository depositosRepository;

    @Mock
    private AposentadoriaRepository aposentadoriaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private DepositosService depositosService;

    @Test
    void depositar_DeveCriarDepositoMensal_QuandoValorDentroDoLimite() {
        Long clienteId = 1L;
        Long aposentadoriaId = 1L;
        Double valorDeposito = 500.0;
        Double valorMensal = 1000.0;

        DepositosRequest request = new DepositosRequest(aposentadoriaId, valorDeposito);

        Aposentadoria aposentadoria = new Aposentadoria();
        aposentadoria.setIdAposentadoria(aposentadoriaId);
        aposentadoria.setValor_mensal(valorMensal);

        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        aposentadoria.setCliente(cliente);

        when(aposentadoriaRepository.findById(aposentadoriaId))
                .thenReturn(Optional.of(aposentadoria));
        when(depositosRepository.findTotalDepositadoNoPeriodo(eq(clienteId), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(300.0);

        Depositos resultado = depositosService.depositar(request);

        assertNotNull(resultado);
        assertEquals(TipoDeposito.MENSAL, resultado.getTipo());
        assertEquals(valorDeposito, resultado.getValor());
        assertEquals(aposentadoria, resultado.getAposentadoria());
        assertEquals(cliente, resultado.getCliente());

        verify(aposentadoriaRepository).findById(aposentadoriaId);
        verify(depositosRepository).findTotalDepositadoNoPeriodo(eq(clienteId), any(LocalDateTime.class), any(LocalDateTime.class));
        verify(depositosRepository).save(any(Depositos.class));
    }

    @Test
    void depositar_DeveCriarDepositoAporte_QuandoDepositoUltrapassaLimite() {
        // Arrange
        Long clienteId = 1L;
        Long aposentadoriaId = 1L;
        Double valorDeposito = 600.0;
        Double valorMensal = 1000.0;

        DepositosRequest request = new DepositosRequest(aposentadoriaId, valorDeposito);

        Aposentadoria aposentadoria = new Aposentadoria();
        aposentadoria.setIdAposentadoria(aposentadoriaId);
        aposentadoria.setValor_mensal(valorMensal);

        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        aposentadoria.setCliente(cliente);

        when(aposentadoriaRepository.findById(aposentadoriaId))
                .thenReturn(Optional.of(aposentadoria));
        when(depositosRepository.findTotalDepositadoNoPeriodo(eq(clienteId), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(800.0);

        Depositos resultado = depositosService.depositar(request);

        assertEquals(TipoDeposito.APORTE, resultado.getTipo());
    }

    @Test
    void depositar_DeveCriarDepositoAporte_QuandoLimiteJaFoiAtingido() {
        // Arrange
        Long clienteId = 1L;
        Long aposentadoriaId = 1L;
        Double valorDeposito = 100.0;
        Double valorMensal = 1000.0;

        DepositosRequest request = new DepositosRequest(aposentadoriaId, valorDeposito);

        Aposentadoria aposentadoria = new Aposentadoria();
        aposentadoria.setIdAposentadoria(aposentadoriaId);
        aposentadoria.setValor_mensal(valorMensal);

        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        aposentadoria.setCliente(cliente);

        when(aposentadoriaRepository.findById(aposentadoriaId))
                .thenReturn(Optional.of(aposentadoria));
        when(depositosRepository.findTotalDepositadoNoPeriodo(eq(clienteId), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(1000.0);

        Depositos resultado = depositosService.depositar(request);

        assertEquals(TipoDeposito.APORTE, resultado.getTipo());
    }

    @Test
    void depositar_DeveCriarDepositoAporte_QuandoDepositoFazUltrapassarLimiteExatamente() {
        Long clienteId = 1L;
        Long aposentadoriaId = 1L;
        Double valorDeposito = 1.0;
        Double valorMensal = 1000.0;

        DepositosRequest request = new DepositosRequest(aposentadoriaId, valorDeposito);

        Aposentadoria aposentadoria = new Aposentadoria();
        aposentadoria.setIdAposentadoria(aposentadoriaId);
        aposentadoria.setValor_mensal(valorMensal);

        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        aposentadoria.setCliente(cliente);

        when(aposentadoriaRepository.findById(aposentadoriaId))
                .thenReturn(Optional.of(aposentadoria));
        when(depositosRepository.findTotalDepositadoNoPeriodo(eq(clienteId), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(1000.0);

        Depositos resultado = depositosService.depositar(request);

        assertEquals(TipoDeposito.APORTE, resultado.getTipo());
    }

    @Test
    void depositar_DeveLancarExcecao_QuandoAposentadoriaNaoEncontrada() {
        Long aposentadoriaId = 999L;
        DepositosRequest request = new DepositosRequest(aposentadoriaId, 500.0);

        when(aposentadoriaRepository.findById(aposentadoriaId))
                .thenReturn(Optional.empty());

        assertThrows(NaoEncontrado.class, () -> depositosService.depositar(request));
    }

    @Test
    void totalDoCliente_DeveRetornarSomaCorreta() {
        Long clienteId = 1L;
        Depositos deposito1 = new Depositos();
        deposito1.setValor(500.0);

        Depositos deposito2 = new Depositos();
        deposito2.setValor(300.0);

        when(depositosRepository.findByClienteId(clienteId))
                .thenReturn(List.of(deposito1, deposito2));

        Double total = depositosService.totalDoCliente(clienteId);

        assertEquals(800.0, total);
    }

    @Test
    void totalDoCliente_DeveRetornarZero_QuandoNaoHaDepositos() {
        Long clienteId = 1L;
        when(depositosRepository.findByClienteId(clienteId))
                .thenReturn(List.of());

        Double total = depositosService.totalDoCliente(clienteId);

        assertEquals(0.0, total);
    }

    @Test
    void totalDaAposentadoria_DeveRetornarSomaCorreta() {
        Long aposentadoriaId = 1L;
        Depositos deposito1 = new Depositos();
        deposito1.setValor(1000.0);

        Depositos deposito2 = new Depositos();
        deposito2.setValor(500.0);

        when(depositosRepository.findByAposentadoriaIdAposentadoria(aposentadoriaId))
                .thenReturn(List.of(deposito1, deposito2));

        Double total = depositosService.totalDaAposentadoria(aposentadoriaId);

        assertEquals(1500.0, total);
    }
}