package com.example.empre_go.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.empre_go.models.Empregador;

@Repository
public interface EmpregadorRepository extends JpaRepository<Empregador, Long> {
    Optional<Empregador> findByEmail(String email);
}