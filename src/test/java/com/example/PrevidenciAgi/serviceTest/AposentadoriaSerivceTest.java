package com.example.PrevidenciAgi.serviceTest;

import com.example.PrevidenciAgi.model.aposentadoria.request.AposentadoriaRequest;
import com.example.PrevidenciAgi.model.aposentadoria.Aposentadoria;
import com.example.PrevidenciAgi.model.cliente.Cliente;
import com.example.PrevidenciAgi.enums.TipoAposentadoria;
import com.example.PrevidenciAgi.repository.AposentadoriaRepository;
import com.example.PrevidenciAgi.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AposentadoriaSerivceTest {

    @Mock
    private AposentadoriaRepository aposentadoriaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private AposentadoriaService aposentadoriaService;

    @Test
    void DeveCriarUma_AposentadoriaValida_CasoNaoExista() {
        Double valor_mensal = 1000.0;
        Double valor_deposito = 0.0;
        LocalDate data_contratada = LocalDate.now();
        LocalDate data_aposentar = LocalDate.of(2040, 3, 28);
        LocalDate data_inicio = LocalDate.of(2026, 4, 12);

        AposentadoriaRequest request = new AposentadoriaRequest(
                TipoAposentadoria.PGBL_PROGRESSIVO,
                valor_mensal,
                valor_deposito,
                data_contratada,
                data_aposentar,
                data_inicio,
                1L
        );
        Aposentadoria aposentadoria = new Aposentadoria();
        aposentadoria.setData_inicio(data_inicio);
        aposentadoria.setData_contratada(data_contratada);
        aposentadoria.setTipoAposentadoria(TipoAposentadoria.PGBL_PROGRESSIVO);
        aposentadoria.setValor_mensal(valor_mensal);
        aposentadoria.setValor_deposito(valor_deposito);
        aposentadoria.setData_aposentar(data_aposentar);


        Cliente cliente = new Cliente();
        when(clienteRepository.findById(request.id())).thenReturn(Optional.of(cliente));

        when(aposentadoriaRepository.existsByClienteId(request.id())).thenReturn(false);

        aposentadoriaService.assinarAposentadoria(request);

        verify(aposentadoriaRepository).save(any(Aposentadoria.class));
    }
}
