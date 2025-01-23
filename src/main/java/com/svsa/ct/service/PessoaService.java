package com.svsa.ct.service;

import com.svsa.ct.model.Denuncia;
import com.svsa.ct.model.Pessoa;
import com.svsa.ct.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> buscarPessoas() {
        return pessoaRepository.findAll();
    }
}
