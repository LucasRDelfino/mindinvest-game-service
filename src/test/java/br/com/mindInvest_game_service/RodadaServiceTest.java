package br.com.mindInvest_game_service;

import br.com.mindInvest_game_service.dto.RodadaDTO;
import br.com.mindInvest_game_service.model.Personagem;
import br.com.mindInvest_game_service.model.Rodada;
import br.com.mindInvest_game_service.repository.PersonagemRepository;
import br.com.mindInvest_game_service.repository.RodadaRepository;
import br.com.mindInvest_game_service.service.RodadaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RodadaServiceTest {

    @Mock
    private RodadaRepository rodadaRepository;

    @Mock
    private PersonagemRepository personagemRepository;

    @InjectMocks
    private RodadaService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarRodadaComSucesso() {
        Personagem personagem = new Personagem(1L, "Lucas", "Jogador", "Aprendendo");
        when(personagemRepository.findById(1L)).thenReturn(Optional.of(personagem));

        RodadaDTO dto = new RodadaDTO(null, "Rodada 1", "Escolha A", "Escolha B", "Boa", "Ruim", 1L);

        Rodada rodadaSalva = Rodada.builder()
                .id(1L)
                .descricao("Rodada 1")
                .escolhaA("Escolha A")
                .escolhaB("Escolha B")
                .personagem(personagem)
                .build();

        when(rodadaRepository.save(any(Rodada.class))).thenReturn(rodadaSalva);

        RodadaDTO resultado = service.criar(dto);

        assertNotNull(resultado);
        assertEquals("Rodada 1", resultado.descricao());
        verify(rodadaRepository, times(1)).save(any(Rodada.class));
    }
}