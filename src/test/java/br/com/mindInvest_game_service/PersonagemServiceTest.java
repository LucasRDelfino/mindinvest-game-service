package br.com.mindInvest_game_service;
import br.com.mindInvest_game_service.dto.PersonagemDTO;
import br.com.mindInvest_game_service.model.Personagem;
import br.com.mindInvest_game_service.repository.PersonagemRepository;
import br.com.mindInvest_game_service.service.PersonagemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PersonagemServiceTest {

    @Mock
    private PersonagemRepository repository;

    @InjectMocks
    private PersonagemService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarPersonagemComSucesso() {
        PersonagemDTO dto = new PersonagemDTO(null, "João", "Investidor", "Aprende a poupar");
        Personagem p = Personagem.builder()
                .id(1L)
                .nome("João")
                .papel("Investidor")
                .descricao("Aprende a poupar")
                .build();

        when(repository.save(any(Personagem.class))).thenReturn(p);

        PersonagemDTO resultado = service.criar(dto);

        assertNotNull(resultado);
        assertEquals("João", resultado.nome());
        verify(repository, times(1)).save(any(Personagem.class));
    }

    @Test
    void deveBuscarPersonagemPorId() {
        Personagem p = new Personagem(1L, "Maria", "Educadora", "Ensina finanças");
        when(repository.findById(1L)).thenReturn(Optional.of(p));

        PersonagemDTO resultado = service.buscarPorId(1L);

        assertEquals("Maria", resultado.nome());
        verify(repository, times(1)).findById(1L);
    }
}