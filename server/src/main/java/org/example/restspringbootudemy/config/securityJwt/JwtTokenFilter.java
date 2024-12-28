package org.example.restspringbootudemy.config.securityJwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenProvider tokenProvider;

    @Autowired
    public JwtTokenFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override // Filtragem aplicada a cada requisição HTTP
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = tokenProvider.resolveToken((HttpServletRequest) servletRequest); // Resolve o token da requisição
        if (token != null && tokenProvider.validateToken(token)) {
            Authentication auth = tokenProvider.getAuthentication(token); // Se o token existir e for válido, obtém a autenticação
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth); // Com a autenticação, configura o contexto de segurança
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
