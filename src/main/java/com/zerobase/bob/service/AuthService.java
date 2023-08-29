package com.zerobase.bob.service;

import com.zerobase.bob.dto.AuthRequestDto;

public interface AuthService {
    boolean signUp(AuthRequestDto.SignUp request);

    String signIn(AuthRequestDto.SignIn request);
}
