package com.example.empre_go.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.empre_go.dto.AtualizarCandidatoDto;
import com.example.empre_go.models.Candidato;
import com.example.empre_go.models.Vaga;
import com.example.empre_go.repositories.CandidatoRepository;
import com.example.empre_go.repositories.VagaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CandidatoService {

    private final CandidatoRepository candidatoRepository;
    private final VagaRepository vagaRepository;

    public Candidato buscar(Long id) {
        return candidatoRepository.findById(id).orElseThrow();
    }

    public Candidato atualizar(Long id, AtualizarCandidatoDto dados) {
        Candidato candidato = buscar(id);

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

        return candidatoRepository.save(candidato);
    }

    public Candidato atualizarNome(Long id, Map<String, String> body) {
        Candidato candidato = buscar(id);
        candidato.setNome(body.get("nome"));
        return candidatoRepository.save(candidato);
    }

    public Candidato atualizarDescricao(Long id, Map<String, String> body) {
        Candidato candidato = buscar(id);
        candidato.setDescricao(body.get("descricao"));
        return candidatoRepository.save(candidato);
    }

    public Candidato atualizarExperiencia(Long id, Map<String, String> body) {
        Candidato candidato = buscar(id);
        candidato.setExperiencia(body.get("experiencia"));
        return candidatoRepository.save(candidato);
    }

    public Candidato atualizarContato(Long id, Map<String, String> body) {
        Candidato candidato = buscar(id);

        if (body.get("telefone") != null) {
            candidato.setTelefone(body.get("telefone"));
        }

        if (body.get("cidade") != null) {
            candidato.setCidade(body.get("cidade"));
        }

        return candidatoRepository.save(candidato);
    }

    public List<Vaga> vagasAplicadas(Long id) {
        return vagaRepository.findAll().stream()
                .filter(vaga -> vaga.getCandidatos().stream()
                        .anyMatch(candidato -> candidato.getId().equals(id)))
                .collect(Collectors.toList());
    }

    public void avaliar(Long id, Double nota) {
        Candidato candidato = buscar(id);

        double media = candidato.getAvaliacao() == null
                ? nota
                : (candidato.getAvaliacao() + nota) / 2;

        candidato.setAvaliacao(media);
        candidatoRepository.save(candidato);
    }
}