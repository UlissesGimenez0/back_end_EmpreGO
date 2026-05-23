package com.example.empre_go.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.empre_go.dto.AtualizarCandidatoDto;
import com.example.empre_go.models.Candidato;
import com.example.empre_go.models.Vaga;
import com.example.empre_go.service.CandidatoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/candidatos")
@RequiredArgsConstructor
public class CandidatoController {

    private final CandidatoService candidatoService;

    @GetMapping("/{id}")
    public ResponseEntity<Candidato> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(candidatoService.buscar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidato> atualizar(
            @PathVariable Long id,
            @RequestBody AtualizarCandidatoDto dados
    ) {
        return ResponseEntity.ok(candidatoService.atualizar(id, dados));
    }

    @PutMapping("/{id}/nome")
    public ResponseEntity<Candidato> atualizarNome(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        return ResponseEntity.ok(candidatoService.atualizarNome(id, body));
    }

    @PutMapping("/{id}/descricao")
    public ResponseEntity<Candidato> atualizarDescricao(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        return ResponseEntity.ok(candidatoService.atualizarDescricao(id, body));
    }

    @PutMapping("/{id}/experiencia")
    public ResponseEntity<Candidato> atualizarExperiencia(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        return ResponseEntity.ok(candidatoService.atualizarExperiencia(id, body));
    }

    @PutMapping("/{id}/contato")
    public ResponseEntity<Candidato> atualizarContato(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        return ResponseEntity.ok(candidatoService.atualizarContato(id, body));
    }

    @GetMapping("/{id}/vagas-aplicadas")
    public ResponseEntity<List<Vaga>> vagasAplicadas(@PathVariable Long id) {
        return ResponseEntity.ok(candidatoService.vagasAplicadas(id));
    }

    @PutMapping("/{id}/avaliar")
    public ResponseEntity<String> avaliar(
            @PathVariable Long id,
            @RequestBody Map<String, Double> body
    ) {
        candidatoService.avaliar(id, body.get("nota"));
        return ResponseEntity.ok("Avaliação salva!");
    }
}