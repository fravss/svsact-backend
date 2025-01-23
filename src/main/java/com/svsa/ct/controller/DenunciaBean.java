package com.svsa.ct.controller;

import com.svsa.ct.dto.DenunciaRecordDto;
import com.svsa.ct.model.Denuncia;
import com.svsa.ct.model.enums.AgenteViolador;
import com.svsa.ct.model.enums.DireitoViolado;
import com.svsa.ct.model.enums.OrigemDenuncia;
import com.svsa.ct.service.DenunciaService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/denuncia")
public class DenunciaBean {
    @Autowired //inject
    DenunciaService denunciaService;


    @PostMapping
    public ResponseEntity<Denuncia> saveDenuncia(@RequestBody @Valid DenunciaRecordDto denunciaRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(denunciaService.saveDenuncia(denunciaRecordDto));
    }


    @GetMapping
    public ResponseEntity<List<Denuncia>> getDenunciasBooks() {
        System.out.println("funfa");
        return ResponseEntity.status(HttpStatus.OK).body(denunciaService.buscarDenuncias());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDenuncia(@PathVariable Long id) {
        denunciaService.apagarDenuncia(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/origem-denuncia")
    public OrigemDenuncia[] getAllorigensDenuncia() {
        return OrigemDenuncia.values();
    }

    @GetMapping("/direito-violado")
    public DireitoViolado[] getAlldireitoViolado() {
        return DireitoViolado.values();
    }
    @GetMapping("/agente-violador")
    public AgenteViolador[] getAllAgenteViolador() {
        return AgenteViolador.values();
    }

}
