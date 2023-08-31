package com.zerobase.bob.service;

import com.zerobase.bob.dto.Auth;

public interface AuthService {
    boolean signUp(Auth.SignUp request);

    String signIn(Auth.SignIn request);
}
