package com.example.empre_go.models;

import lombok.Data;

@Data
public class AtualizarCandidatoDto {
    private String nome;
    private String cidade;
    private String telefone;
    private String descricao;
    private String experiencia;
    private Integer idade;
}