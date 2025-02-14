package com.svsa.ct.service;

import com.svsa.ct.dtos.denunciaDtos.RequestDenunciaDto;


import com.svsa.ct.exceptionsHandler.exceptions.service.DenunciaNaoEncontradaException;

import com.svsa.ct.model.Denuncia;

import com.svsa.ct.model.Usuario;

import com.svsa.ct.model.enums.OrigemDenuncia;
import com.svsa.ct.model.enums.StatusRD;
import com.svsa.ct.repository.DenunciaRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DenunciaService {
    @Autowired
    private DenunciaRepository denunciaRepository;


    @Transactional
    public Denuncia saveDenuncia(RequestDenunciaDto denunciaRecordDto, Usuario usuario) {
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
        var denuncias = denunciaRepository.findAll();

        if (denuncias.isEmpty()) {
            throw new DenunciaNaoEncontradaException( "Nenhuma denuncia foi encontrada");
        }
        return denuncias;
    }

    public Denuncia buscarDenuncia(Long id) {
        return denunciaRepository.findById(id).orElseThrow(() -> new DenunciaNaoEncontradaException(id));
    }

    @Transactional
    public void apagarDenuncia(Long id){
        this.buscarDenuncia(id);
        denunciaRepository.deleteById(id);
    }

    @Transactional
    public Denuncia atualizarDenuncia(RequestDenunciaDto denunciaRecordDto, Long id) {
        System.out.println("no atualizar denuncia");
        Denuncia denuncia = this.buscarDenuncia(id);


        denuncia.setDataEmissao(denunciaRecordDto.dataEmissao());
        denuncia.setRelato(denunciaRecordDto.relato());
        denuncia.setStatusRD(StatusRD.valueOf(denunciaRecordDto.statusRD()));
        denuncia.setOrigemDenuncia(OrigemDenuncia.valueOf(denunciaRecordDto.origemDenuncia()));
        denuncia.setResponsaveis(denunciaRecordDto.responsaveis());
        denuncia.setCriancasAdolescentes(denunciaRecordDto.criancasAdolescentes());
        denuncia.setMedidasAplicadas(denunciaRecordDto.medidasAplicadas());


        return  denunciaRepository.save(denuncia);
    }
}
