package com.example.empre_go.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.empre_go.models.Candidato;
import com.example.empre_go.models.Empregador;
import com.example.empre_go.models.StatusVaga;
import com.example.empre_go.models.Vaga;
import com.example.empre_go.repositories.CandidatoRepository;
import com.example.empre_go.repositories.EmpregadorRepository;
import com.example.empre_go.repositories.VagaRepository;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/vagas")
@RequiredArgsConstructor
public class VagaController {

    private final VagaRepository vagaRepository;
    private final CandidatoRepository candidatoRepository;
    private final EmpregadorRepository empregadorRepository;

    @GetMapping
    public ResponseEntity<List<Vaga>> listar() {
        return ResponseEntity.ok(vagaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vaga> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vagaRepository.findById(id).orElseThrow());
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Vaga vaga) {
        if (vaga.getAutor() == null || vaga.getAutor().getId() == null) {
            return ResponseEntity.badRequest().body("Empregador não informado.");
        }

        Empregador empregador = empregadorRepository.findById(vaga.getAutor().getId())
                .orElse(null);

        if (empregador == null) {
            return ResponseEntity.badRequest().body("Empregador não encontrado.");
        }

        vaga.setAutor(empregador);

        if (vaga.getStatus() == null) {
            vaga.setStatus(StatusVaga.ABERTA);
        }

        return ResponseEntity.ok(vagaRepository.save(vaga));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vaga> atualizar(@PathVariable Long id, @RequestBody Vaga vagaAtualizada) {
        Vaga vaga = vagaRepository.findById(id).orElseThrow();

        if (vagaAtualizada.getTitulo() != null) {
            vaga.setTitulo(vagaAtualizada.getTitulo());
        }

        if (vagaAtualizada.getDescricao() != null) {
            vaga.setDescricao(vagaAtualizada.getDescricao());
        }

        if (vagaAtualizada.getEndereco() != null) {
            vaga.setEndereco(vagaAtualizada.getEndereco());
        }

        if (vagaAtualizada.getStatus() != null) {
            vaga.setStatus(vagaAtualizada.getStatus());
        }

        if (vagaAtualizada.getCandidatoSelecionadoId() != null) {
            vaga.setCandidatoSelecionadoId(vagaAtualizada.getCandidatoSelecionadoId());
        }

        return ResponseEntity.ok(vagaRepository.save(vaga));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        vagaRepository.deleteById(id);
        return ResponseEntity.ok("Vaga removida!");
    }

    @PostMapping("/{id}/candidatar/{candidatoId}")
    public ResponseEntity<String> candidatar(@PathVariable Long id, @PathVariable Long candidatoId) {
        Vaga vaga = vagaRepository.findById(id).orElseThrow();
        Candidato candidato = candidatoRepository.findById(candidatoId).orElseThrow();

        boolean jaCandidatado = vaga.getCandidatos().stream()
                .anyMatch(c -> c.getId().equals(candidatoId));

        if (!jaCandidatado) {
            vaga.getCandidatos().add(candidato);
            vagaRepository.save(vaga);
        }

        return ResponseEntity.ok("Candidatura realizada!");
    }

    @GetMapping("/{id}/candidatos")
    public ResponseEntity<List<Candidato>> listarCandidatos(@PathVariable Long id) {
        return ResponseEntity.ok(vagaRepository.findById(id).orElseThrow().getCandidatos());
    }
}