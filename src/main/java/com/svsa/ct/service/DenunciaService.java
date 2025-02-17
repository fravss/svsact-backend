package com.svsa.ct.service;


import com.svsa.ct.dtos.Response.DenunciaModel;
import com.svsa.ct.exceptionsHandler.exceptions.service.DenunciaNaoEncontradaException;

import com.svsa.ct.model.Denuncia;

import com.svsa.ct.repository.DenunciaRepository;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DenunciaService {
    @Autowired
    private DenunciaRepository denunciaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public Denuncia saveDenuncia(Denuncia denuncia) {
        return denunciaRepository.save(denuncia);
    }

    public List<Denuncia> buscarDenuncias() {
          return denunciaRepository.findAll();
    }

    public Denuncia buscarDenuncia(Long id) {
        return denunciaRepository.findById(id).orElseThrow(() -> new DenunciaNaoEncontradaException(id));
    }

    @Transactional
    public void apagarDenuncia(Long id){
        this.buscarDenuncia(id);
        denunciaRepository.deleteById(id);
    }


}
