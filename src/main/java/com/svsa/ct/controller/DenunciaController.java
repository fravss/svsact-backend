package com.svsa.ct.controller;

import com.svsa.ct.dtos.denunciaDtos.RequestDenunciaDto;
import com.svsa.ct.model.Denuncia;
import com.svsa.ct.model.Usuario;
import com.svsa.ct.model.enums.AgenteViolador;
import com.svsa.ct.model.enums.DireitoViolado;
import com.svsa.ct.model.enums.OrigemDenuncia;
import com.svsa.ct.service.DenunciaService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/denuncia")
public class DenunciaController {
    @Autowired //inject
    DenunciaService denunciaService;


    @PostMapping
    public ResponseEntity<Denuncia> saveDenuncia(@RequestBody @Valid RequestDenunciaDto denunciaRecordDto) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Denuncia denuncia = denunciaService.saveDenuncia(denunciaRecordDto, usuario);
        denuncia.getConselheiro().setSenha(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(denuncia);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Denuncia> getDenunciaById(@PathVariable Long id) {
        Denuncia denuncia = denunciaService.buscarDenuncia(id);
        denuncia.getConselheiro().setSenha(null);
        return ResponseEntity.status(HttpStatus.OK).body(denuncia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Denuncia> atualizarDenuncia(@RequestBody @Valid RequestDenunciaDto denunciaRecordDto, @PathVariable Long id) {
        Denuncia denuncia = denunciaService.atualizarDenuncia(denunciaRecordDto, id);
        denuncia.getConselheiro().setSenha(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(denuncia);
    }


    @GetMapping
    public ResponseEntity<List<Denuncia>> getDenuncias() {
        List<Denuncia> denuncias = denunciaService.buscarDenuncias();
        denuncias.forEach(d -> d.getConselheiro().setSenha(null));
        return ResponseEntity.status(HttpStatus.OK).body(denuncias);
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
