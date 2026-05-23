package com.example.empre_go.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.empre_go.models.Candidato;
import com.example.empre_go.models.Empregador;
import com.example.empre_go.repositories.CandidatoRepository;
import com.example.empre_go.repositories.EmpregadorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CandidatoRepository candidatoRepository;
    private final EmpregadorRepository empregadorRepository;

    public void registrar(Map<String, String> body) {
        if (body.get("role").equals("CANDIDATO")) {
            Candidato candidato = new Candidato();
            candidato.setNome(body.get("nome"));
            candidato.setEmail(body.get("email"));
            candidato.setSenha(body.get("senha"));
            candidato.setCidade(body.get("cidade"));
            candidato.setTelefone(body.get("telefone"));

            if (body.get("idade") != null) {
                candidato.setIdade(Integer.parseInt(body.get("idade")));
            }

            candidatoRepository.save(candidato);
            return;
        }

        Empregador empregador = new Empregador();
        empregador.setNome(body.get("nome"));
        empregador.setEmail(body.get("email"));
        empregador.setSenha(body.get("senha"));

        empregadorRepository.save(empregador);
    }

    public String login(Map<String, String> body) {
        String email = body.get("email");
        String senha = body.get("senha");
        String role = body.get("role");

        if (role.equals("CANDIDATO")) {
            Candidato candidato = candidatoRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            if (!candidato.getSenha().equals(senha)) {
                throw new RuntimeException("Senha incorreta");
            }

            return "Login realizado! Id: " + candidato.getId();
        }

        Empregador empregador = empregadorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!empregador.getSenha().equals(senha)) {
            throw new RuntimeException("Senha incorreta");
        }

        return "Login realizado! Id: " + empregador.getId();
    }
}