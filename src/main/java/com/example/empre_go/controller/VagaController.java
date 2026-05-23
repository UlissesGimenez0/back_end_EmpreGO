package com.example.empre_go.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.empre_go.dto.VagaResponseDto;
import com.example.empre_go.models.Candidato;
import com.example.empre_go.models.Vaga;
import com.example.empre_go.service.VagaService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/vagas")
@RequiredArgsConstructor
public class VagaController {

    private final VagaService vagaService;

    @GetMapping
    public ResponseEntity<List<VagaResponseDto>> listar() {
        return ResponseEntity.ok(vagaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vaga> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vagaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Vaga vaga) {
        try {
            return ResponseEntity.ok(vagaService.criar(vaga));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vaga> atualizar(
            @PathVariable Long id,
            @RequestBody Vaga vagaAtualizada
    ) {
        return ResponseEntity.ok(vagaService.atualizar(id, vagaAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        vagaService.deletar(id);
        return ResponseEntity.ok("Vaga removida!");
    }

    @PostMapping("/{id}/candidatar/{candidatoId}")
    public ResponseEntity<String> candidatar(
            @PathVariable Long id,
            @PathVariable Long candidatoId
    ) {
        vagaService.candidatar(id, candidatoId);
        return ResponseEntity.ok("Candidatura realizada!");
    }

    @GetMapping("/{id}/candidatos")
    public ResponseEntity<List<Candidato>> listarCandidatos(@PathVariable Long id) {
        return ResponseEntity.ok(vagaService.listarCandidatos(id));
    }
}