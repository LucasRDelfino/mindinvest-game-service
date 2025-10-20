package br.com.mindInvest_game_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rodada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private String escolhaA;
    private String escolhaB;
    private String consequenciaA;
    private String consequenciaB;

    @ManyToOne
    @JoinColumn(name = "personagem_id")
    private Personagem personagem;
}
