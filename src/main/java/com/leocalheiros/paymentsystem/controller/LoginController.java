package com.leocalheiros.paymentsystem.controller;

import com.leocalheiros.paymentsystem.dto.AuthenticationRequest;
import com.leocalheiros.paymentsystem.dto.AuthenticationResponse;
import com.leocalheiros.paymentsystem.entity.User;
import com.leocalheiros.paymentsystem.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

    public LoginController(TokenService tokenService, AuthenticationManager authenticationManager){
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    @Operation(summary = "Login user with email and password",
            description = "Login user with email and password",
            tags = {"Auth"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    }),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(mediaType = "application/json")),
            @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity login(@RequestBody AuthenticationRequest authenticationRequest){
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.email(), authenticationRequest.password()
            );

            var auth = authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());

            return ResponseEntity.ok(new AuthenticationResponse(token));
        }catch (BadCredentialsException e){
            throw new com.leocalheiros.paymentsystem.exceptions.BadCredentialsException("Invalid email/password credentials");
        }
    }
}
