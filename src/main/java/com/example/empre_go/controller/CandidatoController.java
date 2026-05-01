package com.example.empre_go.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.empre_go.dto.AtualizarCandidatoDto;
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
        Candidato candidato = candidatoRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(candidato);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidato> atualizar(
            @PathVariable Long id,
            @RequestBody AtualizarCandidatoDto dados
    ) {
        Candidato candidato = candidatoRepository.findById(id).orElseThrow();

        if (dados.getNome() != null) {
            candidato.setNome(dados.getNome());
        }

        if (dados.getCidade() != null) {
            candidato.setCidade(dados.getCidade());
        }

        if (dados.getTelefone() != null) {
            candidato.setTelefone(dados.getTelefone());
        }

        if (dados.getDescricao() != null) {
            candidato.setDescricao(dados.getDescricao());
        }

        if (dados.getExperiencia() != null) {
            candidato.setExperiencia(dados.getExperiencia());
        }

        if (dados.getIdade() != null) {
            candidato.setIdade(dados.getIdade());
        }

        Candidato candidatoAtualizado = candidatoRepository.save(candidato);

        return ResponseEntity.ok(candidatoAtualizado);
    }

    @PutMapping("/{id}/nome")
    public ResponseEntity<Candidato> atualizarNome(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        Candidato candidato = candidatoRepository.findById(id).orElseThrow();

        candidato.setNome(body.get("nome"));

        return ResponseEntity.ok(candidatoRepository.save(candidato));
    }

    @PutMapping("/{id}/descricao")
    public ResponseEntity<Candidato> atualizarDescricao(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        Candidato candidato = candidatoRepository.findById(id).orElseThrow();

        candidato.setDescricao(body.get("descricao"));

        return ResponseEntity.ok(candidatoRepository.save(candidato));
    }

    @PutMapping("/{id}/experiencia")
    public ResponseEntity<Candidato> atualizarExperiencia(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        Candidato candidato = candidatoRepository.findById(id).orElseThrow();

        candidato.setExperiencia(body.get("experiencia"));

        return ResponseEntity.ok(candidatoRepository.save(candidato));
    }

    @PutMapping("/{id}/contato")
    public ResponseEntity<Candidato> atualizarContato(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        Candidato candidato = candidatoRepository.findById(id).orElseThrow();

        if (body.get("telefone") != null) {
            candidato.setTelefone(body.get("telefone"));
        }

        if (body.get("cidade") != null) {
            candidato.setCidade(body.get("cidade"));
        }

        return ResponseEntity.ok(candidatoRepository.save(candidato));
    }

    @GetMapping("/{id}/vagas-aplicadas")
    public ResponseEntity<List<Vaga>> vagasAplicadas(@PathVariable Long id) {
        List<Vaga> vagas = vagaRepository.findAll().stream()
                .filter(vaga -> vaga.getCandidatos().stream()
                        .anyMatch(candidato -> candidato.getId().equals(id)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(vagas);
    }

    @PutMapping("/{id}/avaliar")
    public ResponseEntity<String> avaliar(
            @PathVariable Long id,
            @RequestBody Map<String, Double> body
    ) {
        Candidato candidato = candidatoRepository.findById(id).orElseThrow();

        double nota = body.get("nota");
        double media = candidato.getAvaliacao() == null
                ? nota
                : (candidato.getAvaliacao() + nota) / 2;

        candidato.setAvaliacao(media);
        candidatoRepository.save(candidato);

        return ResponseEntity.ok("Avaliação salva!");
    }
}