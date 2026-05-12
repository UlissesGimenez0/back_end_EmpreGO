package com.example.empre_go.dto;

import com.example.empre_go.models.StatusVaga;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VagaResponseDto {
    private Long id;
    private String titulo;
    private String descricao;
    private String nomeEmpresa;
    private String endereco;
    private String tempoMedioEstimado;
    private Double lat;
    private Double lng;
    private StatusVaga status;
    private Long candidatoSelecionadoId;
    private Long autorId;
}