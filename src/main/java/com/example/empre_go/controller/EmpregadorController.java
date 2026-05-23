package com.example.empre_go.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.empre_go.models.Empregador;
import com.example.empre_go.service.EmpregadorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/empregadores")
@RequiredArgsConstructor
public class EmpregadorController {

    private final EmpregadorService empregadorService;

    @GetMapping
    public ResponseEntity<List<Empregador>> listar() {
        return ResponseEntity.ok(empregadorService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empregador> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(empregadorService.buscar(id));
    }

    @PutMapping("/{id}/avaliar")
    public ResponseEntity<String> avaliar(
            @PathVariable Long id,
            @RequestBody Map<String, Double> body
    ) {
        empregadorService.avaliar(id, body.get("nota"));
        return ResponseEntity.ok("Avaliação salva!");
    }
}