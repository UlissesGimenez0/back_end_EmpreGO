package com.example.empre_go.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vagas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descricao;

    private String nomeEmpresa;
    private String endereco;
    private String tempoMedioEstimado;

    @Enumerated(EnumType.STRING)
    private StatusVaga status = StatusVaga.ABERTA;

    private Double lat;
    private Double lng;

    @ManyToOne
    @JoinColumn(name = "empregador_id")
    private Empregador autor;

    @ManyToMany
    @JoinTable(
            name = "candidaturas",
            joinColumns = @JoinColumn(name = "vaga_id"),
            inverseJoinColumns = @JoinColumn(name = "candidato_id")
    )
    private List<Candidato> candidatos = new ArrayList<>();

    private Long candidatoSelecionadoId;
}
