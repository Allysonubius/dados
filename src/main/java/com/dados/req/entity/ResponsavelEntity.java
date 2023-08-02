package com.dados.req.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "responsaveis")
public class ResponsavelEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private PacienteEntity paciente;
    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;
    @Column(name = "parentesco")
    private String parentesco;

}
