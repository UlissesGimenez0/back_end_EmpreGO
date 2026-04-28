package com.example.empre_go.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.empre_go.models.StatusVaga;
import com.example.empre_go.models.Vaga;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {
    List<Vaga> findByStatus(StatusVaga status);
    List<Vaga> findByAutorId(Long autorId);
}
