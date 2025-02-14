package com.svsa.ct.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.svsa.ct.exceptionsHandler.exceptions.infra.SecretNaoEncontradoException;
import com.svsa.ct.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Usuario usuario){
        if (secret == null) {
            throw new SecretNaoEncontradoException();
        }
            Algorithm algoritmo = Algorithm.HMAC256(secret);
                return JWT.create()
                        .withIssuer("svsact-api")
                        .withSubject(usuario.getEmail())
                        .withExpiresAt(genExpirationDate())
                        .sign(algoritmo);

    }

    public String validateToken(String token){

        if (secret == null) {
            throw new SecretNaoEncontradoException();
        }
            Algorithm algorithm = Algorithm.HMAC256(secret);

                return JWT.require(algorithm)
                        .withIssuer("svsact-api")
                        .build()
                        .verify(token)
                        .getSubject();


    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
