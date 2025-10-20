package br.com.mindInvest_game_service.dto;

public record RodadaDTO(Long id, String descricao, String escolhaA, String escolhaB,
                        String consequenciaA, String consequenciaB, Long personagemId) {}
