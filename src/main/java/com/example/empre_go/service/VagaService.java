package com.example.empre_go.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.empre_go.dto.VagaResponseDto;
import com.example.empre_go.models.Candidato;
import com.example.empre_go.models.Empregador;
import com.example.empre_go.models.StatusVaga;
import com.example.empre_go.models.Vaga;
import com.example.empre_go.repositories.CandidatoRepository;
import com.example.empre_go.repositories.EmpregadorRepository;
import com.example.empre_go.repositories.VagaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VagaService {

    private final VagaRepository vagaRepository;
    private final CandidatoRepository candidatoRepository;
    private final EmpregadorRepository empregadorRepository;

    public List<VagaResponseDto> listar() {
        return vagaRepository.findAll().stream()
                .map(this::toResponseDto)
                .toList();
    }

    public Vaga buscarPorId(Long id) {
        return vagaRepository.findById(id).orElseThrow();
    }

    public Vaga criar(Vaga vaga) {
        if (vaga.getAutor() == null || vaga.getAutor().getId() == null) {
            throw new RuntimeException("Empregador não informado.");
        }

        Empregador empregador = empregadorRepository.findById(vaga.getAutor().getId())
                .orElseThrow(() -> new RuntimeException("Empregador não encontrado."));

        vaga.setAutor(empregador);

        if (vaga.getStatus() == null) {
            vaga.setStatus(StatusVaga.ABERTA);
        }

        return vagaRepository.save(vaga);
    }

    public Vaga atualizar(Long id, Vaga vagaAtualizada) {
        Vaga vaga = buscarPorId(id);

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

        return vagaRepository.save(vaga);
    }

    public void deletar(Long id) {
        vagaRepository.deleteById(id);
    }

    public void candidatar(Long id, Long candidatoId) {
        Vaga vaga = buscarPorId(id);
        Candidato candidato = candidatoRepository.findById(candidatoId).orElseThrow();

        boolean jaCandidatado = vaga.getCandidatos().stream()
                .anyMatch(c -> c.getId().equals(candidatoId));

        if (!jaCandidatado) {
            vaga.getCandidatos().add(candidato);
            vagaRepository.save(vaga);
        }
    }

    public List<Candidato> listarCandidatos(Long id) {
        return buscarPorId(id).getCandidatos();
    }

    private VagaResponseDto toResponseDto(Vaga vaga) {
        return new VagaResponseDto(
                vaga.getId(),
                vaga.getTitulo(),
                vaga.getDescricao(),
                vaga.getAutor().getNome(),
                vaga.getEndereco(),
                vaga.getTempoMedioEstimado(),
                vaga.getLat(),
                vaga.getLng(),
                vaga.getStatus(),
                vaga.getCandidatoSelecionadoId(),
                vaga.getAutor().getId()
        );
    }
}