package com.svsa.ct.service;

import com.svsa.ct.dto.DenunciaRecordDto;
import com.svsa.ct.model.Denuncia;

import com.svsa.ct.model.Usuario;
import com.svsa.ct.model.enums.AgenteViolador;

import com.svsa.ct.model.enums.OrigemDenuncia;
import com.svsa.ct.model.enums.StatusRD;
import com.svsa.ct.repository.DenunciaRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DenunciaService {
    @Autowired
    private DenunciaRepository denunciaRepository;


    @Transactional
    public Denuncia saveDenuncia(DenunciaRecordDto denunciaRecordDto, Usuario usuario) {
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

        return denunciaRepository.save(denuncia);
    }

    public List<Denuncia> buscarDenuncias() {
        return denunciaRepository.findAll();
    }

    @Transactional
    public void apagarDenuncia(Long id){
        denunciaRepository.deleteById(id);
    }


}
