package br.com.mindInvest_game_service.controller;

import br.com.mindInvest_game_service.dto.RodadaDTO;
import br.com.mindInvest_game_service.service.RodadaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rodadas")
public class RodadaController {

    private final RodadaService service;

    public RodadaController(RodadaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RodadaDTO> criar(@RequestBody RodadaDTO dto) {
        return ResponseEntity.ok(service.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<RodadaDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RodadaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/personagem/{personagemId}")
    public ResponseEntity<List<RodadaDTO>> listarPorPersonagem(@PathVariable Long personagemId) {
        return ResponseEntity.ok(service.listarPorPersonagem(personagemId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RodadaDTO> atualizar(@PathVariable Long id, @RequestBody RodadaDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}