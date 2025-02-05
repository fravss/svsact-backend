package com.svsa.ct.controller;

import com.svsa.ct.dtos.autenticacaoDtos.LoginDto;
import com.svsa.ct.dtos.autenticacaoDtos.RespostaLoginDto;

import com.svsa.ct.infra.security.TokenService;
import com.svsa.ct.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity autenticar(@RequestBody @Valid LoginDto autenticacaoDto) {
        var senhaDoUsuario = new UsernamePasswordAuthenticationToken(autenticacaoDto.email(), autenticacaoDto.senha());
        var auth = this.authenticationManager.authenticate(senhaDoUsuario);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new RespostaLoginDto(token));
    }
}
