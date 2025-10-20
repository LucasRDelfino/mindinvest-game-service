package br.com.mindInvest_game_service.repository;

import br.com.mindInvest_game_service.model.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonagemRepository extends JpaRepository<Personagem, Long> {}