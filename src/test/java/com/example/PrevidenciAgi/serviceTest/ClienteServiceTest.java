package com.example.PrevidenciAgi.serviceTest;

import com.example.PrevidenciAgi.component.JwtTokenGenerator;
import com.example.PrevidenciAgi.dto.cliente.Cliente;
import com.example.PrevidenciAgi.Enum.AtualizacaoDeDados;
import com.example.PrevidenciAgi.Enum.Genero;
import com.example.PrevidenciAgi.Enum.Role;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import com.example.PrevidenciAgi.service.ClienteService;
import com.example.PrevidenciAgi.service.exception.EscolhaEnumInvalida;
import com.example.PrevidenciAgi.service.exception.JaExistente;
import com.example.PrevidenciAgi.service.exception.NaoEncontrado;
import com.example.PrevidenciAgi.service.exception.SenhaInvalida;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenGenerator jwtTokenGenerator;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void login_DeveRetornarToken_QuandoCredenciaisEstaoCorretas() {
        String email = "cliente@teste.com";
        String senha = "senha123";
        String senhaCriptografada = "senha_criptografada";
        String tokenEsperado = "jwt_token";

        Cliente cliente = new Cliente();
        cliente.setEmail(email);
        cliente.setSenha(senhaCriptografada);
        cliente.setRole(Role.CLIENTE);

        when(clienteRepository.findByEmail(email))
                .thenReturn(Optional.of(cliente));
        when(passwordEncoder.matches(senha, senhaCriptografada))
                .thenReturn(true);
        when(jwtTokenGenerator.generateToken(any()))
                .thenReturn(tokenEsperado);

        String token = clienteService.login(email, senha);

        assertEquals(tokenEsperado, token);
        verify(clienteRepository).findByEmail(email);
        verify(passwordEncoder).matches(senha, senhaCriptografada);
        verify(jwtTokenGenerator).generateToken(any());
    }

    @Test
    void login_DeveLancarExcecao_QuandoEmailNaoEncontrado() {
        String email = "naoexiste@teste.com";
        String senha = "senha123";

        when(clienteRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        assertThrows(NaoEncontrado.class, () -> clienteService.login(email, senha));
        verify(clienteRepository).findByEmail(email);
        verifyNoInteractions(passwordEncoder);
        verifyNoInteractions(jwtTokenGenerator);
    }

    @Test
    void login_DeveLancarExcecao_QuandoSenhaIncorreta() {
        String email = "cliente@teste.com";
        String senha = "senhaErrada";
        String senhaCriptografada = "senha_criptografada";

        Cliente cliente = new Cliente();
        cliente.setEmail(email);
        cliente.setSenha(senhaCriptografada);

        when(clienteRepository.findByEmail(email))
                .thenReturn(Optional.of(cliente));
        when(passwordEncoder.matches(senha, senhaCriptografada))
                .thenReturn(false);

        assertThrows(SenhaInvalida.class, () -> clienteService.login(email, senha));
        verify(clienteRepository).findByEmail(email);
        verify(passwordEncoder).matches(senha, senhaCriptografada);
        verifyNoInteractions(jwtTokenGenerator);
    }

    @Test
    void cadastrarCliente_DeveSalvarCliente_QuandoDadosSaoValidos() {
        Cliente cliente = new Cliente();
        cliente.setCpf("123.456.789-00");
        cliente.setSenha("senhaSegura123");
        cliente.setGenero(Genero.MASCULINO);
        cliente.setEmail("cliente@teste.com");

        String senhaCriptografada = "senha_criptografada";

        when(clienteRepository.existsByCpf(cliente.getCpf()))
                .thenReturn(false);
        when(passwordEncoder.encode("senhaSegura123"))
                .thenReturn(senhaCriptografada);

        clienteService.CadastrarCliente(cliente);

        verify(clienteRepository).existsByCpf(cliente.getCpf());
        verify(passwordEncoder).encode("senhaSegura123");
        verify(clienteRepository).save(cliente);
        assertEquals(senhaCriptografada, cliente.getSenha());
    }

    @Test
    void cadastrarCliente_DeveLancarExcecao_QuandoCPFJaExiste() {
        Cliente cliente = new Cliente();
        cliente.setCpf("123.456.789-00");
        cliente.setSenha("senhaSegura123");
        cliente.setGenero(Genero.MASCULINO);

        when(clienteRepository.existsByCpf(cliente.getCpf()))
                .thenReturn(true);

        assertThrows(JaExistente.class, () -> clienteService.CadastrarCliente(cliente));
        verify(clienteRepository).existsByCpf(cliente.getCpf());
        verifyNoInteractions(passwordEncoder);
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void cadastrarCliente_DeveLancarExcecao_QuandoSenhaMuitoPequena() {
        Cliente cliente = new Cliente();
        cliente.setCpf("123.456.789-00");
        cliente.setSenha("123");
        cliente.setGenero(Genero.MASCULINO);

        when(clienteRepository.existsByCpf(cliente.getCpf()))
                .thenReturn(false);

        assertThrows(SenhaInvalida.class, () -> clienteService.CadastrarCliente(cliente));
        verify(clienteRepository).existsByCpf(cliente.getCpf());
        verifyNoInteractions(passwordEncoder);
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void atualizarDados_DeveAtualizarEmail_QuandoDadoEEmail() {
        Long id = 1L;
        AtualizacaoDeDados dado = AtualizacaoDeDados.EMAIL;
        String novoEmail = "novoemail@teste.com";

        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setEmail("emailantigo@teste.com");

        when(clienteRepository.findById(id))
                .thenReturn(Optional.of(cliente));

        clienteService.atualizarDados(id, dado, novoEmail);

        assertEquals(novoEmail, cliente.getEmail());
        verify(clienteRepository).findById(id);
        verify(clienteRepository).save(cliente);
    }

    @Test
    void atualizarDados_DeveAtualizarSenha_QuandoDadoESenha() {
        Long id = 1L;
        AtualizacaoDeDados dado = AtualizacaoDeDados.SENHA;
        String novaSenha = "novaSenha123";
        String novaSenhaCriptografada = "nova_senha_criptografada";

        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setSenha("senha_antiga");

        when(clienteRepository.findById(id))
                .thenReturn(Optional.of(cliente));
        when(passwordEncoder.encode(novaSenha))
                .thenReturn(novaSenhaCriptografada);

        clienteService.atualizarDados(id, dado, novaSenha);

        assertEquals(novaSenhaCriptografada, cliente.getSenha());
        verify(clienteRepository).findById(id);
        verify(passwordEncoder).encode(novaSenha);
        verify(clienteRepository).save(cliente);
    }

    @Test
    void atualizarDados_DeveLancarExcecao_QuandoClienteNaoEncontrado() {
        Long id = 999L;
        AtualizacaoDeDados dado = AtualizacaoDeDados.EMAIL;
        String novoValor = "novo@teste.com";

        when(clienteRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(NaoEncontrado.class, () -> clienteService.atualizarDados(id, dado, novoValor));
        verify(clienteRepository).findById(id);
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void atualizarDados_DeveLancarExcecao_QuandoCampoInvalido() {
        Long id = 1L;
        AtualizacaoDeDados dado = AtualizacaoDeDados.EMAIL;
        String novoValor = "Novo Nome";

        Cliente cliente = new Cliente();
        cliente.setId(id);

        when(clienteRepository.findById(id))
                .thenReturn(Optional.of(cliente));

        assertThrows(EscolhaEnumInvalida.class, () -> clienteService.atualizarDados(id, dado, novoValor));
        verify(clienteRepository).findById(id);
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void atualizarDados_DeveFuncionarComCaseInsensitive() {
        // Arrange
        Long id = 1L;
        AtualizacaoDeDados dado = AtualizacaoDeDados.EMAIL;
        String novoEmail = "novoemail@teste.com";

        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setEmail("emailantigo@teste.com");

        when(clienteRepository.findById(id))
                .thenReturn(Optional.of(cliente));

        clienteService.atualizarDados(id, dado, novoEmail);

        assertEquals(novoEmail, cliente.getEmail());
        verify(clienteRepository).findById(id);
        verify(clienteRepository).save(cliente);
    }

    @Test
    void cadastrarCliente_DeveAceitarSenhaCom8Caracteres() {
        Cliente cliente = new Cliente();
        cliente.setCpf("123.456.789-00");
        cliente.setSenha("12345678");
        cliente.setGenero(Genero.MASCULINO);

        when(clienteRepository.existsByCpf(cliente.getCpf()))
                .thenReturn(false);
        when(passwordEncoder.encode(cliente.getSenha()))
                .thenReturn("senha_criptografada");

        assertDoesNotThrow(() -> clienteService.CadastrarCliente(cliente));
        verify(clienteRepository).save(cliente);
    }

    @Test
    void atualizarDados_DeveManterDadosNaoAlterados() {
        Long id = 1L;
        AtualizacaoDeDados dado = AtualizacaoDeDados.EMAIL;
        String novoEmail = "novoemail@teste.com";

        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setEmail("emailantigo@teste.com");
        cliente.setSenha("senha_antiga");
        cliente.setCpf("123.456.789-00");

        when(clienteRepository.findById(id))
                .thenReturn(Optional.of(cliente));

        clienteService.atualizarDados(id, dado, novoEmail);

        assertEquals(novoEmail, cliente.getEmail());
        assertEquals("senha_antiga", cliente.getSenha());
        assertEquals("123.456.789-00", cliente.getCpf());
    }
}