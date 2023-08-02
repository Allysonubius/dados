package com.dados.req.DTO;

import lombok.Data;

@Data
public class ResponsavelDTO {
    private Long id;
    private Long pacienteId;
    private String nome;
    private String telefone;
    private String parentesco;

    // Construtores, getters e setters
}