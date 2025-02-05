package com.svsa.ct.service;

import com.svsa.ct.dtos.denunciaDtos.AtualizarDenunciaRecordDto;
import com.svsa.ct.dtos.denunciaDtos.CriarDenunciaRecordDto;
import com.svsa.ct.dtos.denunciaDtos.RespostaDenunciaRecordDto;
import com.svsa.ct.dtos.usuarioDtos.UsuarioRecordDto;
import com.svsa.ct.model.Denuncia;

import com.svsa.ct.model.Usuario;

import com.svsa.ct.model.enums.OrigemDenuncia;
import com.svsa.ct.model.enums.StatusRD;
import com.svsa.ct.repository.DenunciaRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DenunciaService {
    @Autowired
    private DenunciaRepository denunciaRepository;


    @Transactional
    public RespostaDenunciaRecordDto saveDenuncia(CriarDenunciaRecordDto denunciaRecordDto, Usuario usuario) {
        System.out.println(denunciaRecordDto);

        Denuncia denuncia = new Denuncia();


        denuncia.setDataEmissao(denunciaRecordDto.dataEmissao());
        denuncia.setConselheiro(usuario);
        denuncia.setRelato(denunciaRecordDto.relato());
        denuncia.setStatusRD(StatusRD.valueOf(denunciaRecordDto.statusRD()));
        denuncia.setOrigemDenuncia(OrigemDenuncia.valueOf(denunciaRecordDto.origemDenuncia()));
        denuncia.setResponsaveis(denunciaRecordDto.responsaveis());
        denuncia.setCriancasAdolescentes(denunciaRecordDto.criancasAdolescentes());
        denuncia.setMedidasAplicadas(denunciaRecordDto.medidasAplicadas());

        Denuncia denunciaSalva = denunciaRepository.save(denuncia);
        return new RespostaDenunciaRecordDto(
                denunciaSalva.getId(),
                new UsuarioRecordDto(denunciaSalva.getConselheiro().getId(), denunciaSalva.getConselheiro().getNome(), denunciaSalva.getConselheiro().getEmail()),
                denunciaSalva.getDataEmissao(),
                denunciaSalva.getRelato(),
                denunciaSalva.getResponsaveis(),
                denunciaSalva.getCriancasAdolescentes(),
                denunciaSalva.getMedidasAplicadas(),
                denunciaSalva.getOrigemDenuncia().toString(),
                denunciaSalva.getStatusRD().toString()
        );
    }

    public List<Denuncia> buscarDenuncias() {
        return denunciaRepository.findAll();
    }

    public RespostaDenunciaRecordDto buscarDenuncia(Long id) {
        Denuncia denuncia = denunciaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new RespostaDenunciaRecordDto(
                denuncia.getId(),
                new UsuarioRecordDto(denuncia.getConselheiro().getId(), denuncia.getConselheiro().getNome(), denuncia.getConselheiro().getEmail()),
                denuncia.getDataEmissao(),
                denuncia.getRelato(),
                denuncia.getResponsaveis(),
                denuncia.getCriancasAdolescentes(),
                denuncia.getMedidasAplicadas(),
                denuncia.getOrigemDenuncia().toString(),
                denuncia.getStatusRD().toString()
        );
    }

    @Transactional
    public void apagarDenuncia(Long id){
        denunciaRepository.deleteById(id);
    }

    @Transactional
    public RespostaDenunciaRecordDto atualizarDenuncia(AtualizarDenunciaRecordDto denunciaRecordDto, Long id) {
        Denuncia denuncia = denunciaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Denúncia não encontrada"));

        denuncia.setDataEmissao(denunciaRecordDto.dataEmissao());
        denuncia.setRelato(denunciaRecordDto.relato());
        denuncia.setStatusRD(StatusRD.valueOf(denunciaRecordDto.statusRD()));
        denuncia.setOrigemDenuncia(OrigemDenuncia.valueOf(denunciaRecordDto.origemDenuncia()));
        denuncia.setResponsaveis(denunciaRecordDto.responsaveis());
        denuncia.setCriancasAdolescentes(denunciaRecordDto.criancasAdolescentes());
        denuncia.setMedidasAplicadas(denunciaRecordDto.medidasAplicadas());

        Denuncia denunciaSalva = denunciaRepository.save(denuncia);
        return new RespostaDenunciaRecordDto(
                denunciaSalva.getId(),
                new UsuarioRecordDto(denunciaSalva.getConselheiro().getId(), denunciaSalva.getConselheiro().getNome(), denunciaSalva.getConselheiro().getEmail()),
                denunciaSalva.getDataEmissao(),
                denunciaSalva.getRelato(),
                denunciaSalva.getResponsaveis(),
                denunciaSalva.getCriancasAdolescentes(),
                denunciaSalva.getMedidasAplicadas(),
                denunciaSalva.getOrigemDenuncia().toString(),
                denunciaSalva.getStatusRD().toString()
        );
    }
}
