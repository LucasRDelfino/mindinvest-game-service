package br.com.mindInvest_game_service.service;

import br.com.mindInvest_game_service.dto.RodadaDTO;
import br.com.mindInvest_game_service.model.Personagem;
import br.com.mindInvest_game_service.model.Rodada;
import br.com.mindInvest_game_service.repository.PersonagemRepository;
import br.com.mindInvest_game_service.repository.RodadaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RodadaService {

    private final RodadaRepository rodadaRepository;
    private final PersonagemRepository personagemRepository;

    public RodadaService(RodadaRepository rodadaRepository, PersonagemRepository personagemRepository) {
        this.rodadaRepository = rodadaRepository;
        this.personagemRepository = personagemRepository;
    }

    public RodadaDTO criar(RodadaDTO dto) {
        Personagem personagem = personagemRepository.findById(dto.personagemId())
                .orElseThrow(() -> new RuntimeException("Personagem não encontrado"));

        Rodada r = Rodada.builder()
                .descricao(dto.descricao())
                .escolhaA(dto.escolhaA())
                .escolhaB(dto.escolhaB())
                .consequenciaA(dto.consequenciaA())
                .consequenciaB(dto.consequenciaB())
                .personagem(personagem)
                .build();

        rodadaRepository.save(r);
        return toDTO(r);
    }

    public List<RodadaDTO> listar() {
        return rodadaRepository.findAll().stream().map(this::toDTO).toList();
    }

    public List<RodadaDTO> listarPorPersonagem(Long personagemId) {
        return rodadaRepository.findByPersonagemId(personagemId).stream().map(this::toDTO).toList();
    }

    public RodadaDTO buscarPorId(Long id) {
        Rodada r = rodadaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rodada não encontrada"));
        return toDTO(r);
    }

    public RodadaDTO atualizar(Long id, RodadaDTO dto) {
        Rodada r = rodadaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rodada não encontrada"));

        Personagem personagem = personagemRepository.findById(dto.personagemId())
                .orElseThrow(() -> new RuntimeException("Personagem não encontrado"));

        r.setDescricao(dto.descricao());
        r.setEscolhaA(dto.escolhaA());
        r.setEscolhaB(dto.escolhaB());
        r.setConsequenciaA(dto.consequenciaA());
        r.setConsequenciaB(dto.consequenciaB());
        r.setPersonagem(personagem);

        rodadaRepository.save(r);
        return toDTO(r);
    }

    public void deletar(Long id) {
        if (!rodadaRepository.existsById(id)) {
            throw new RuntimeException("Rodada não encontrada");
        }
        rodadaRepository.deleteById(id);
    }

    private RodadaDTO toDTO(Rodada r) {
        return new RodadaDTO(r.getId(), r.getDescricao(), r.getEscolhaA(), r.getEscolhaB(),
                r.getConsequenciaA(), r.getConsequenciaB(), r.getPersonagem().getId());
    }
}

