package com.dados.req.controller;

import com.dados.req.DTO.PacienteDTO;
import com.dados.req.DTO.ResponsavelDTO;
import com.dados.req.entity.PacienteEntity;
import com.dados.req.service.DadosService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DadosController {

    private final DadosService dadosService;

    @GetMapping("/pacientes")
    public ResponseEntity<Page<PacienteDTO>> listarPacientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<PacienteDTO> pacientes = this.dadosService.listarTodosPacientes(pageable);
        if (pacientes.isEmpty() && pacientes == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pacientes);
        }
    }

    @PostMapping("/pacientes")
    public  ResponseEntity<PacienteDTO> cadastrarPaciente(@RequestBody PacienteDTO pacienteDTO){
        PacienteDTO novoPaciente = this.dadosService.cadastrarPaciente(pacienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPaciente);
    }

    @PostMapping("responsaveis")
    public ResponseEntity<ResponsavelDTO> cadastrarResponsavel(@RequestBody ResponsavelDTO responsavelDTO){
        ResponsavelDTO novoResponsavel = this.dadosService.cadastrarResponsavel(responsavelDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoResponsavel);
    }
}
