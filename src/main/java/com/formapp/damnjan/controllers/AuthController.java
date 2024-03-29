package com.formapp.damnjan.controllers;

import com.formapp.damnjan.models.request.RefreshTokenRequestDto;
import com.formapp.damnjan.models.request.SingInRequestDto;
import com.formapp.damnjan.models.request.SingUpRequestDto;
import com.formapp.damnjan.models.response.AuthResponseDto;
import com.formapp.damnjan.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Endpoint for creating new User")
    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody SingUpRequestDto signUpRequestDto) {
        authService.signUp(signUpRequestDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Endpoint for logging User")
    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDto> signIn(@RequestBody SingInRequestDto signInRequestDto) {
        return ResponseEntity.ok(authService.signIn(signInRequestDto));
    }

    @Operation(summary = "Endpoint for refreshing token")
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refresh(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequestDto));
    }
}
