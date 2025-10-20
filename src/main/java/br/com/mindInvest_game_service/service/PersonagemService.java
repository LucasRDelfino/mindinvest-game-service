package br.com.mindInvest_game_service.service;

import br.com.mindInvest_game_service.dto.PersonagemDTO;
import br.com.mindInvest_game_service.model.Personagem;
import br.com.mindInvest_game_service.repository.PersonagemRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonagemService {

    private final PersonagemRepository repository;

    public PersonagemService(PersonagemRepository repository) {
        this.repository = repository;
    }

    public PersonagemDTO criar(PersonagemDTO dto) {
        Personagem p = Personagem.builder()
                .nome(dto.nome())
                .papel(dto.papel())
                .descricao(dto.descricao())
                .build();
        repository.save(p);
        return new PersonagemDTO(p.getId(), p.getNome(), p.getPapel(), p.getDescricao());
    }

    public List<PersonagemDTO> listar() {
        return repository.findAll().stream()
                .map(p -> new PersonagemDTO(p.getId(), p.getNome(), p.getPapel(), p.getDescricao()))
                .toList();
    }

    public PersonagemDTO buscarPorId(Long id) {
        Personagem p = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personagem não encontrado"));
        return new PersonagemDTO(p.getId(), p.getNome(), p.getPapel(), p.getDescricao());
    }

    public PersonagemDTO atualizar(Long id, PersonagemDTO dto) {
        Personagem p = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personagem não encontrado"));
        p.setNome(dto.nome());
        p.setPapel(dto.papel());
        p.setDescricao(dto.descricao());
        repository.save(p);
        return new PersonagemDTO(p.getId(), p.getNome(), p.getPapel(), p.getDescricao());
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Personagem não encontrado");
        }
        repository.deleteById(id);
    }
}


