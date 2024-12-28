package org.example.restspringbootudemy.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.restspringbootudemy.dto.security.AccountCredentialsVO;
import org.example.restspringbootudemy.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthService service;
    
    private boolean checkIfSignInParamsIsNull(AccountCredentialsVO data) {
        return data == null || data.getUsername() == null || data.getUsername().isBlank() || data.getPassword() == null || data.getPassword().isBlank();
    }

    private boolean checkIfRefreshParamsIsNull(String refreshToken, String username) {
        return refreshToken == null || refreshToken.isBlank() || username == null || username.isBlank();
    }

    @SuppressWarnings("rawtypes")
    @PostMapping(value = "/signin")
    @Operation(summary = "Authenticates a user and returns a token")
    public ResponseEntity signIn(@RequestBody AccountCredentialsVO data) {
        if (checkIfSignInParamsIsNull(data)) {return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");}
        var token = service.signin(data);
        if (token == null) {return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");}
        return token;
    }

    @SuppressWarnings("rawtypes")
    @PutMapping(value = "/refresh/{username}")
    @Operation(summary = "Refresh token for authenticated user and returns a token")
    public ResponseEntity refreshToken(@PathVariable("username") String username, @RequestHeader("Authorization") String refreshToken) {
        if (checkIfRefreshParamsIsNull(refreshToken, username)) {return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");}
        var token = service.refreshToken(username, refreshToken);
        if (token == null) {return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");}
        return token;
    }

}
