package com.svsa.ct.core.security;

import com.svsa.ct.core.exception.TokenNaoEncontradoException;
import com.svsa.ct.domain.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UsuarioRepository userRepository;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
          try {


              var token = this.recoverToken(request);
               if (request.getRequestURI().startsWith("/api/restricted/") && token == null) {
                   throw new TokenNaoEncontradoException();
               } else if(request.getRequestURI().startsWith("/api/restricted/") && token != null) {
                   var login = tokenService.validateToken(token);
                   UserDetails user = userRepository.findByEmail(login);

                   var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                   SecurityContextHolder.getContext().setAuthentication(authentication);


               }


              filterChain.doFilter(request, response);
          } catch (Exception e) {

                resolver.resolveException(request, response, null, e);
          }
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}