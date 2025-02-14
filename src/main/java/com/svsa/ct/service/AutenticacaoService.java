package com.svsa.ct.service;

import com.svsa.ct.exceptionsHandler.exceptions.service.UsuarioNaoEncontradoException;
import com.svsa.ct.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email)  {
        UserDetails usuario = usuarioRepository.findByEmail(email);

        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");
        } else {
            return usuario;
        }

    }
}
