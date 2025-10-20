package br.com.mindInvest_game_service.repository;

import br.com.mindInvest_game_service.model.Rodada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RodadaRepository extends JpaRepository<Rodada, Long> {
    List<Rodada> findByPersonagemId(Long personagemId);
}
