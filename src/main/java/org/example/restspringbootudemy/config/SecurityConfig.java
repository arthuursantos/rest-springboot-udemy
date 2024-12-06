package org.example.restspringbootudemy.config;

import org.example.restspringbootudemy.config.securityJwt.JwtTokenFilter;
import org.example.restspringbootudemy.config.securityJwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Bean // Configura o codificador de senhas para comparações com as do BD. Usa algoritmo PKBDF2.
    PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>(); // Cria um map de encriptadores
        Pbkdf2PasswordEncoder pbkdf2encoder = new Pbkdf2PasswordEncoder("", 8, 185000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
        encoders.put("pbkdf2", pbkdf2encoder);
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2encoder); // Define o PKBDF2 como padrão para comparações
        return passwordEncoder;
    }

    @Bean // Expõe o AuthenticationManager como bean
    AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean // Configura os filtros para segurança HTTP
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtTokenFilter customFilter = new JwtTokenFilter(tokenProvider);
        return http
                .httpBasic(AbstractHttpConfigurer::disable) // Desabilita autenticação básica
                .csrf(AbstractHttpConfigurer::disable)  // Desabilita proteção CSRF
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class) // Add o custom filter antes do padrão
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sessão stateless, sem estado
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers("/auth/signin", "/auth/refresh/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll() // Acesso livre
                                .requestMatchers("/api/**").authenticated() // Acesso com autenticação
                                .requestMatchers("/users").denyAll() // Sem nenhum acesso
                )
                .cors(cors -> {})
                .build();
    }

}