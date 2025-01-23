package com.svsa.ct.service;

import com.svsa.ct.dto.DenunciaRecordDto;
import com.svsa.ct.model.Denuncia;
import com.svsa.ct.model.Pessoa;
import com.svsa.ct.model.enums.AgenteViolador;
import com.svsa.ct.model.enums.DireitoViolado;
import com.svsa.ct.model.enums.OrigemDenuncia;
import com.svsa.ct.model.enums.StatusRD;
import com.svsa.ct.repository.DenunciaRepository;
import com.svsa.ct.repository.PessoaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@Service
public class DenunciaService {
    @Autowired
    private DenunciaRepository denunciaRepository;
    @Autowired
    private PessoaRepository pessoaRepository;

    @Transactional
    public Denuncia saveDenuncia(DenunciaRecordDto denunciaRecordDto) {
        System.out.println(denunciaRecordDto);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Denuncia denuncia = new Denuncia();


        denuncia.setDataEmissao(denunciaRecordDto.dataEmissao());

        denuncia.setAgenteViolador(AgenteViolador.valueOf(denunciaRecordDto.agenteViolador()));
        denuncia.setRelato(denunciaRecordDto.relato());
        denuncia.setStatusRD(StatusRD.valueOf(denunciaRecordDto.statusRD()));
        denuncia.setDireitosViolados(Arrays.asList(denunciaRecordDto.direitosViolados()));
        denuncia.setOrigemDenuncia(OrigemDenuncia.valueOf(denunciaRecordDto.origemDenuncia()));


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
