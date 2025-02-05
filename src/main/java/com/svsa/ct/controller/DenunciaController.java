package com.svsa.ct.controller;

import com.svsa.ct.dtos.denunciaDtos.RequestDenunciaDto;
import com.svsa.ct.dtos.denunciaDtos.ResponseDenunciaDto;
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
    public ResponseEntity<ResponseDenunciaDto> saveDenuncia(@RequestBody @Valid RequestDenunciaDto denunciaRecordDto) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(denunciaService.saveDenuncia(denunciaRecordDto, usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDenunciaDto> getDenunciaById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(denunciaService.buscarDenuncia(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDenunciaDto> atualizarDenuncia(@RequestBody @Valid RequestDenunciaDto denunciaRecordDto, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(denunciaService.atualizarDenuncia(denunciaRecordDto, id));
    }


    @GetMapping
    public ResponseEntity<List<Denuncia>> getDenuncias() {
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
