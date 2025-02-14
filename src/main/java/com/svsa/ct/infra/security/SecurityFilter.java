package com.svsa.ct.infra.security;

import com.svsa.ct.exceptionsHandler.exceptions.infra.TokenNaoEncontradoException;
import com.svsa.ct.repository.UsuarioRepository;
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
import java.util.List;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UsuarioRepository userRepository;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    private static final List<String> EXCLUDED_PATHS = List.of("/auth/login"); // colocar as rotas nao protegidas aqui

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
          try {

              String path = request.getRequestURI();

              if (EXCLUDED_PATHS.contains(path)) {
                  filterChain.doFilter(request, response);
                  return;
              }
              var token = this.recoverToken(request);
               if (token == null) {
                   throw new TokenNaoEncontradoException();
               }

              var login = tokenService.validateToken(token);
              UserDetails user = userRepository.findByEmail(login);

              var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
              SecurityContextHolder.getContext().setAuthentication(authentication);


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