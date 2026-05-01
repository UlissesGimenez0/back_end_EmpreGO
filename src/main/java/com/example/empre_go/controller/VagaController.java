package com.example.empre_go.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.empre_go.models.Candidato;
import com.example.empre_go.models.Vaga;
import com.example.empre_go.repositories.CandidatoRepository;
import com.example.empre_go.repositories.VagaRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vagas")
@RequiredArgsConstructor
public class VagaController {

    private final VagaRepository vagaRepository;
    private final CandidatoRepository candidatoRepository;

    @GetMapping
    public ResponseEntity<List<Vaga>> listar() {
        return ResponseEntity.ok(vagaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vaga> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vagaRepository.findById(id).orElseThrow());
    }

    @PostMapping
    public ResponseEntity<Vaga> criar(@RequestBody Vaga vaga) {
        return ResponseEntity.ok(vagaRepository.save(vaga));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vaga> atualizar(@PathVariable Long id, @RequestBody Vaga vagaAtualizada) {
        Vaga vaga = vagaRepository.findById(id).orElseThrow();
        vaga.setTitulo(vagaAtualizada.getTitulo());
        vaga.setDescricao(vagaAtualizada.getDescricao());
        vaga.setEndereco(vagaAtualizada.getEndereco());
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
        vaga.getCandidatos().add(candidato);
        vagaRepository.save(vaga);
        return ResponseEntity.ok("Candidatura realizada!");
    }

    @GetMapping("/{id}/candidatos")
    public ResponseEntity<List<Candidato>> listarCandidatos(@PathVariable Long id) {
        return ResponseEntity.ok(vagaRepository.findById(id).orElseThrow().getCandidatos());
    }
}
