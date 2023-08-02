package com.dados.req.service;

import com.dados.req.DTO.PacienteDTO;
import com.dados.req.DTO.ResponsavelDTO;
import com.dados.req.entity.PacienteEntity;
import com.dados.req.entity.ResponsavelEntity;
import com.dados.req.repository.PacienteRepository;
import com.dados.req.repository.ResponsavelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DadosService {

    private final PacienteRepository pacienteRepository;
    private final ResponsavelRepository responsavelRepository;

    public Page<PacienteDTO>listarTodosPacientes(Pageable pageable){
        Page<PacienteEntity> pacienteEntities = this.pacienteRepository.findAll(pageable);

        List<PacienteDTO> pacienteDTOS = new ArrayList<>();
        for(PacienteEntity paciente : pacienteEntities){
            PacienteDTO pacienteDTO = new PacienteDTO();

            pacienteDTO.setId(paciente.getId());
            pacienteDTO.setNome(paciente.getNome());
            pacienteDTO.setDataNascimento(paciente.getDataNascimento());
            pacienteDTO.setEndereco(paciente.getEndereco());
            pacienteDTO.setTipoSanguineo(paciente.getTipoSanquineo());
            pacienteDTO.setVacinado(paciente.isVacinado());
            pacienteDTO.setCep(paciente.getCep());

            List<ResponsavelDTO> responsaveisDTO = new ArrayList<>();
            for(ResponsavelEntity responsavel  : paciente.getResponsavelEntities()){
                ResponsavelDTO responsavelDTO = new ResponsavelDTO();

                responsavelDTO.setPacienteId(responsavel.getPaciente().getId());
                responsavelDTO.setId(responsavel.getId());
                responsavelDTO.setNome(responsavel.getNome());
                responsavelDTO.setTelefone(responsavel.getTelefone());
                responsavelDTO.setParentesco(responsavel.getParentesco());

                responsaveisDTO.add(responsavelDTO);
            }

            pacienteDTO.setResponsaveis(responsaveisDTO);
            pacienteDTOS.add(pacienteDTO);
        }

        return new PageImpl<>(pacienteDTOS,pageable,pacienteEntities.getTotalElements());
    }

    public PacienteDTO cadastrarPaciente(PacienteDTO pacienteDTO) {
        PacienteEntity pacienteEntity = new PacienteEntity();
        pacienteEntity.setNome(pacienteDTO.getNome());
        pacienteEntity.setDataNascimento(pacienteDTO.getDataNascimento());
        pacienteEntity.setEndereco(pacienteDTO.getEndereco());
        pacienteEntity.setTipoSanquineo(pacienteDTO.getTipoSanguineo());
        pacienteEntity.setVacinado(pacienteDTO.isVacinado());
        pacienteEntity.setCep(pacienteDTO.getCep());

        List<ResponsavelEntity> responsaveis = new ArrayList<>();
        if (pacienteDTO.getResponsaveis() != null) {
            for (ResponsavelDTO responsavelDTO : pacienteDTO.getResponsaveis()) {
                ResponsavelEntity responsavelEntity = new ResponsavelEntity();
                responsavelEntity.setNome(responsavelDTO.getNome());
                responsavelEntity.setTelefone(responsavelDTO.getTelefone());
                responsavelEntity.setParentesco(responsavelDTO.getParentesco());
                responsavelEntity.setPaciente(pacienteEntity);

                responsaveis.add(responsavelEntity);
            }
        }
        pacienteEntity.setResponsavelEntities(responsaveis);

        PacienteEntity pacienteSalvo = pacienteRepository.save(pacienteEntity);

        return convertToPacienteDTO(pacienteSalvo);
    }

    private PacienteDTO convertToPacienteDTO(PacienteEntity pacienteEntity) {
        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setId(pacienteEntity.getId());
        pacienteDTO.setNome(pacienteEntity.getNome());
        pacienteDTO.setDataNascimento(pacienteEntity.getDataNascimento());
        pacienteDTO.setEndereco(pacienteEntity.getEndereco());
        pacienteDTO.setTipoSanguineo(pacienteEntity.getTipoSanquineo());
        pacienteDTO.setVacinado(pacienteEntity.isVacinado());
        pacienteDTO.setCep(pacienteEntity.getCep());

        List<ResponsavelDTO> responsaveisDTO = new ArrayList<>();
        if (pacienteEntity.getResponsavelEntities() != null) {
            for (ResponsavelEntity responsavelEntity : pacienteEntity.getResponsavelEntities()) {
                ResponsavelDTO responsavelDTO = new ResponsavelDTO();
                responsavelDTO.setId(responsavelEntity.getId());
                responsavelDTO.setNome(responsavelEntity.getNome());
                responsavelDTO.setTelefone(responsavelEntity.getTelefone());
                responsavelDTO.setParentesco(responsavelEntity.getParentesco());
                responsavelDTO.setPacienteId(responsavelEntity.getPaciente().getId());

                responsaveisDTO.add(responsavelDTO);
            }
        }
        pacienteDTO.setResponsaveis(responsaveisDTO);

        return pacienteDTO;
    }

    public ResponsavelDTO cadastrarResponsavel(ResponsavelDTO responsavelDTO) {
        ResponsavelEntity responsavelEntity = new ResponsavelEntity();
        responsavelEntity.setNome(responsavelDTO.getNome());
        responsavelEntity.setTelefone(responsavelDTO.getTelefone());
        responsavelEntity.setParentesco(responsavelDTO.getParentesco());

        PacienteEntity pacienteEntity = pacienteRepository.findById(responsavelDTO.getPacienteId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente com ID " + responsavelDTO.getPacienteId() + " não encontrado."));

        responsavelEntity.setPaciente(pacienteEntity);

        ResponsavelEntity responsavelSalvo = this.responsavelRepository.save(responsavelEntity);

        return convertToResponsavelDTO(responsavelSalvo);
    }

    private ResponsavelDTO convertToResponsavelDTO(ResponsavelEntity responsavelEntity) {
        ResponsavelDTO responsavelDTO = new ResponsavelDTO();
        responsavelDTO.setId(responsavelEntity.getId());
        responsavelDTO.setNome(responsavelEntity.getNome());
        responsavelDTO.setTelefone(responsavelEntity.getTelefone());
        responsavelDTO.setParentesco(responsavelEntity.getParentesco());
        responsavelDTO.setPacienteId(responsavelEntity.getPaciente().getId());

        return responsavelDTO;
    }
}
