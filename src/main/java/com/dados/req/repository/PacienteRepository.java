package com.dados.req.repository;

import com.dados.req.entity.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<PacienteEntity,Long> {
}
