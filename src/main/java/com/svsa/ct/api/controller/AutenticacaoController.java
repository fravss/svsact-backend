package com.svsa.ct.api.controller;

import com.svsa.ct.api.dtos.Request.AutenticacaoRequest;

import com.svsa.ct.api.dtos.Response.AutenticacaoResponse;
import com.svsa.ct.api.exceptionsHandler.ExceptionMessage;
import com.svsa.ct.core.security.TokenService;
import com.svsa.ct.domain.model.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Autenticação") // muda o nome da tag no swagger
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


    @Operation(summary = "Gerar o token de autenticação")
    @ApiResponses(value = { //CONFIGURAÇÕES DO SWAGGER
            @ApiResponse(responseCode = "200", description = "Ok", content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema=
             @Schema(implementation = AutenticacaoResponse.class))}),
             @ApiResponse(responseCode = "404", description = """
        1. Usuário não encontrado.
        2. Senha incorreta.
        """, content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))}),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = {
                    @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionMessage.class))})
    })
    @PostMapping("/login")
        public ResponseEntity autenticar(@RequestBody @Valid AutenticacaoRequest autenticacaoDto) {

            var senhaDoUsuario = new UsernamePasswordAuthenticationToken(autenticacaoDto.getEmail(), autenticacaoDto.getSenha());
            var auth = this.authenticationManager.authenticate(senhaDoUsuario);
            var token = tokenService.generateToken((Usuario) auth.getPrincipal());
            log.info("Token gerado com sucesso: {}", token);
            return ResponseEntity.ok(new AutenticacaoResponse(token));

    }
}
