package com.zerobase.bob.controller;

import com.zerobase.bob.dto.AuthRequestDto;
import com.zerobase.bob.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<Boolean> signUp (@RequestBody AuthRequestDto.SignUp request) {

        return ResponseEntity.ok(authService.signUp(request));
    }

    @PostMapping("/signIn")
    public ResponseEntity<String> signIn (@RequestBody AuthRequestDto.SignIn request) {

        return ResponseEntity.ok(authService.signIn(request));
    }
}
