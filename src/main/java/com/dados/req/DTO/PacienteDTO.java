package com.dados.req.DTO;
import lombok.Data;

import java.util.List;

@Data
public class PacienteDTO {
    private Long id;
    private String nome;
    private String dataNascimento;
    private String endereco;
    private String tipoSanguineo;
    private boolean vacinado;
    private String cep;
    private List<ResponsavelDTO> responsaveis;

    // Construtores, getters e setters
}
