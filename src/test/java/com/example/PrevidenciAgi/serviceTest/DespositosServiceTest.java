package com.example.PrevidenciAgi.serviceTest;

import com.example.PrevidenciAgi.model.deposito.request.DepositosRequest;
import com.example.PrevidenciAgi.model.aposentadoria.Aposentadoria;
import com.example.PrevidenciAgi.model.cliente.Cliente;
import com.example.PrevidenciAgi.model.deposito.Depositos;
import com.example.PrevidenciAgi.Enum.TipoDeposito;
import com.example.PrevidenciAgi.repository.AposentadoriaRepository;
import com.example.PrevidenciAgi.repository.DepositosRepository;
import com.example.PrevidenciAgi.service.DepositosService;
import com.example.PrevidenciAgi.service.exception.NaoEncontrado;
import com.example.PrevidenciAgi.service.exception.TempoInsuficiente;
import com.example.PrevidenciAgi.service.exception.ValorInvalido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
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

        Depositos depositoSalvo = new Depositos();
        depositoSalvo.setTipo(TipoDeposito.MENSAL);
        depositoSalvo.setValor(valorDeposito);
        depositoSalvo.setAposentadoria(aposentadoria);
        depositoSalvo.setCliente(cliente);

        when(aposentadoriaRepository.findById(aposentadoriaId))
                .thenReturn(Optional.of(aposentadoria));
        when(depositosRepository.findTotalDepositadoNoPeriodo(eq(clienteId), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(300.0);
        when(depositosRepository.save(any(Depositos.class))).thenReturn(depositoSalvo);

        Depositos resultado = depositosService.depositar(request);

        assertNotNull(resultado, "O resultado não deve ser nulo");
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

        Depositos depositoSalvo = new Depositos();
        depositoSalvo.setTipo(TipoDeposito.APORTE);
        depositoSalvo.setValor(valorDeposito);
        depositoSalvo.setAposentadoria(aposentadoria);
        depositoSalvo.setCliente(cliente);

        when(aposentadoriaRepository.findById(aposentadoriaId))
                .thenReturn(Optional.of(aposentadoria));
        when(depositosRepository.findTotalDepositadoNoPeriodo(eq(clienteId), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(800.0);
        when(depositosRepository.save(any(Depositos.class))).thenReturn(depositoSalvo);

        Depositos resultado = depositosService.depositar(request);

        assertEquals(TipoDeposito.APORTE, resultado.getTipo());
    }

    @Test
    void depositar_DeveCriarDepositoAporte_QuandoLimiteJaFoiAtingido() {
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

        when(depositosRepository.save(any(Depositos.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Depositos resultado = depositosService.depositar(request);

        assertNotNull(resultado, "O resultado não deve ser nulo");
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

        when(depositosRepository.save(any(Depositos.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Depositos resultado = depositosService.depositar(request);

        assertNotNull(resultado, "O resultado não deve ser nulo");
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

    @Test
    void saqueAdiantado_DeveRetornarValor_QuandoCondicoesSatisfeitas() {
        Long id = 1L;
        Double valor = 1000.0;
        Double valorImposto = 1250.0; // 1000 + 25%
        Double saldoInicial = 2000.0;

        Aposentadoria aposentadoria = new Aposentadoria();
        aposentadoria.setIdAposentadoria(id);
        aposentadoria.setSaldo(saldoInicial);
        aposentadoria.setData_inicio(LocalDate.now().minusYears(3)); // Mais de 2 anos

        when(aposentadoriaRepository.findById(id)).thenReturn(Optional.of(aposentadoria));
        when(aposentadoriaRepository.save(any(Aposentadoria.class))).thenReturn(aposentadoria);

        Double resultado = depositosService.saqueAdiantado(id, valor);

        assertEquals(valor, resultado);
        assertEquals(saldoInicial - valorImposto, aposentadoria.getSaldo());
        verify(aposentadoriaRepository).findById(id);
        verify(aposentadoriaRepository).save(aposentadoria);
    }

    @Test
    void saqueAdiantado_DeveLancarExcecao_QuandoAposentadoriaNaoEncontrada() {
        Long id = 1L;
        Double valor = 1000.0;

        when(aposentadoriaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NaoEncontrado.class, () -> depositosService.saqueAdiantado(id, valor));
        verify(aposentadoriaRepository).findById(id);
        verify(aposentadoriaRepository, never()).save(any());
    }

    @Test
    void saqueAdiantado_DeveLancarExcecao_QuandoSaldoInsuficiente() {
        Long id = 1L;
        Double valor = 1000.0;
        Double saldoInicial = 1200.0;

        Aposentadoria aposentadoria = new Aposentadoria();
        aposentadoria.setIdAposentadoria(id);
        aposentadoria.setSaldo(saldoInicial);
        aposentadoria.setData_inicio(LocalDate.now().minusYears(3));

        when(aposentadoriaRepository.findById(id)).thenReturn(Optional.of(aposentadoria));

        assertThrows(ValorInvalido.class, () -> depositosService.saqueAdiantado(id, valor));
        verify(aposentadoriaRepository).findById(id);
        verify(aposentadoriaRepository, never()).save(any());
    }

    @Test
    void saqueAdiantado_DeveLancarExcecao_QuandoTempoInsuficiente() {
        Long id = 1L;
        Double valor = 1000.0;
        Double saldoInicial = 2000.0;

        Aposentadoria aposentadoria = new Aposentadoria();
        aposentadoria.setIdAposentadoria(id);
        aposentadoria.setSaldo(saldoInicial);
        aposentadoria.setData_inicio(LocalDate.now().minusYears(1));

        when(aposentadoriaRepository.findById(id)).thenReturn(Optional.of(aposentadoria));

        assertThrows(TempoInsuficiente.class, () -> depositosService.saqueAdiantado(id, valor));
        verify(aposentadoriaRepository).findById(id);
        verify(aposentadoriaRepository, never()).save(any());
    }

    @Test
    void saqueAdiantado_DeveLancarExcecao_QuandoDataInicioAnoAtual() {
        Long id = 1L;
        Double valor = 1000.0;
        Double saldoInicial = 2000.0;

        Aposentadoria aposentadoria = new Aposentadoria();
        aposentadoria.setIdAposentadoria(id);
        aposentadoria.setSaldo(saldoInicial);
        aposentadoria.setData_inicio(LocalDate.now());

        when(aposentadoriaRepository.findById(id)).thenReturn(Optional.of(aposentadoria));

        assertThrows(TempoInsuficiente.class, () -> depositosService.saqueAdiantado(id, valor));
        verify(aposentadoriaRepository).findById(id);
        verify(aposentadoriaRepository, never()).save(any());
    }

    @Test
    void saqueAdiantado_DeveCalcularCorretamenteValorImposto() {
        Long id = 1L;
        Double valor = 800.0;
        Double valorImpostoEsperado = 1000.0;
        Double saldoInicial = 1500.0;

        Aposentadoria aposentadoria = new Aposentadoria();
        aposentadoria.setIdAposentadoria(id);
        aposentadoria.setSaldo(saldoInicial);
        aposentadoria.setData_inicio(LocalDate.now().minusYears(5));

        when(aposentadoriaRepository.findById(id)).thenReturn(Optional.of(aposentadoria));
        when(aposentadoriaRepository.save(any(Aposentadoria.class))).thenReturn(aposentadoria);

        Double resultado = depositosService.saqueAdiantado(id, valor);

        assertEquals(valor, resultado);
        assertEquals(saldoInicial - valorImpostoEsperado, aposentadoria.getSaldo());
    }
}