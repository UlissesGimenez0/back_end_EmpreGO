package com.example.empre_go.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.empre_go.models.Empregador;
import com.example.empre_go.repositories.EmpregadorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpregadorService {

    private final EmpregadorRepository empregadorRepository;

    public List<Empregador> listar() {
        return empregadorRepository.findAll();
    }

    public Empregador buscar(Long id) {
        return empregadorRepository.findById(id).orElseThrow();
    }

    public void avaliar(Long id, Double nota) {
        Empregador empregador = buscar(id);

        double media = empregador.getAvaliacao() == null
                ? nota
                : (empregador.getAvaliacao() + nota) / 2;

        empregador.setAvaliacao(media);
        empregadorRepository.save(empregador);
    }
}