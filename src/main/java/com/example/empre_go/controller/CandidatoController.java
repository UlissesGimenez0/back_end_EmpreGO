package com.example.empre_go.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/candidatos")
@RequiredArgsConstructor
public class CandidatoController {

    private final CandidatoRepository candidatoRepository;
    private final VagaRepository vagaRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Candidato> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(candidatoRepository.findById(id).orElseThrow());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidato> atualizar(@PathVariable Long id, @RequestBody Candidato dados) {
        Candidato c = candidatoRepository.findById(id).orElseThrow();
        c.setNome(dados.getNome());
        c.setCidade(dados.getCidade());
        c.setTelefone(dados.getTelefone());
        c.setDescricao(dados.getDescricao());
        c.setExperiencia(dados.getExperiencia());
        return ResponseEntity.ok(candidatoRepository.save(c));
    }

    @GetMapping("/{id}/vagas-aplicadas")
    public ResponseEntity<List<Vaga>> vagasAplicadas(@PathVariable Long id) {
        List<Vaga> vagas = vagaRepository.findAll().stream()
            .filter(v -> v.getCandidatos().stream().anyMatch(c -> c.getId().equals(id)))
            .collect(Collectors.toList());
        return ResponseEntity.ok(vagas);
    }

    @PutMapping("/{id}/avaliar")
    public ResponseEntity<String> avaliar(@PathVariable Long id, @RequestBody Map<String, Double> body) {
        Candidato c = candidatoRepository.findById(id).orElseThrow();
        double nota = body.get("nota");
        double media = c.getAvaliacao() == null ? nota : (c.getAvaliacao() + nota) / 2;
        c.setAvaliacao(media);
        candidatoRepository.save(c);
        return ResponseEntity.ok("Avaliação salva!");
    }
}
