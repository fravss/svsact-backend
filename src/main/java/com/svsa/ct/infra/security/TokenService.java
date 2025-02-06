package com.svsa.ct.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.svsa.ct.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Usuario usuario){
            Algorithm algoritmo = Algorithm.HMAC256(secret);

            try {
                return JWT.create()
                        .withIssuer("svsact-api")
                        .withSubject(usuario.getEmail())
                        .withExpiresAt(genExpirationDate())
                        .sign(algoritmo);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao gerar token");
            }
    }

    public String validateToken(String token){

            Algorithm algorithm = Algorithm.HMAC256(secret);
            try{
                return JWT.require(algorithm)
                        .withIssuer("svsact-api")
                        .build()
                        .verify(token)
                        .getSubject();
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Erro ao tentar validar token");
            }

    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
