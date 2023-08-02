package com.dados.req.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@Table(name = "pacientes")
public class PacienteEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_nascimento")
    private String dataNascimento;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "tipo_sanguineo")
    private String tipoSanquineo;

    @Column(name = "vacinado")
    private boolean vacinado;

    @Column(name = "cep")
    private String cep;

    @OneToMany(mappedBy = "paciente",cascade = CascadeType.ALL)
    @JsonIgnore // ignore a serialização recursiva de responsividade
    private List<ResponsavelEntity> responsavelEntities;
}
