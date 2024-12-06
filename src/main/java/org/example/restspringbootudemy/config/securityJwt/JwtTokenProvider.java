package org.example.restspringbootudemy.config.securityJwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.example.restspringbootudemy.dto.security.TokenVO;
import org.example.restspringbootudemy.exceptions.InvalidJwtAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validity = 3600000; // 3600000ms -  1h

    @Autowired
    private UserDetailsService userDetailsService;

    Algorithm algorithm = null;

    @PostConstruct // Metodo executado após injeção de dependências
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey);
    }

    public TokenVO createAccessToken(String username, List<String> roles) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + validity);
        return TokenVO.builder()
                .username(username)
                .authenticated(true)
                .created(now)
                .expiration(expiresAt)
                .accessToken(getAccessToken(username, roles, now, expiresAt))
                .refreshToken(getRefreshToken(username, roles, now))
                .build();
    }

    public TokenVO refreshToken(String refreshToken) {
        if (refreshToken.contains("Bearer ")) {
            refreshToken = refreshToken.substring("Bearer ".length());
        }
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);
        return createAccessToken(decodedJWT.getSubject(), decodedJWT.getClaim("roles").asList(String.class));
    }

    private String getAccessToken(String username, List<String> roles, Date now, Date expiresAt) {
        String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(expiresAt)
                .withSubject(username)
                .withIssuer(issuerUrl)
                .sign(algorithm)
                .strip();
    }

    private String getRefreshToken(String username, List<String> roles, Date now) {
        Date expiresAt = new Date(now.getTime() + (validity * 3)); // 3h
        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(expiresAt)
                .withSubject(username)
                .sign(algorithm)
                .strip();
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decodedToken(token); // Decodifica o token
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(decodedJWT.getSubject()); // Carrega os detalhes do user
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()); // Cria o objeto de auth do spring security
    }

    public DecodedJWT decodedToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        try {
            return !decodedJWT.getExpiresAt().before(new Date());
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token!");
        }
    }

}
