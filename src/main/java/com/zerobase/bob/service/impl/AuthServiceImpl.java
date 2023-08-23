package com.zerobase.bob.service.impl;

import com.zerobase.bob.dto.Auth;
import com.zerobase.bob.entity.User;
import com.zerobase.bob.exception.CustomException;
import com.zerobase.bob.repository.UserRepository;
import com.zerobase.bob.security.TokenProvider;
import com.zerobase.bob.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.zerobase.bob.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public boolean signUp(Auth.SignUp request) {

        boolean requestUser = userRepository.existsByEmail(request.getEmail());

        if (requestUser) {
            throw new CustomException(ALREADY_REGISTERED_USER);
        }

        String encPassword =
                BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encPassword)
                .build();
        userRepository.save(user);

        return true;
    }

    @Override
    public String signIn(Auth.SignIn request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (!this.passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(UNMATCHED_INFORMATION);
        }

        return tokenProvider
                .generateToken(user.getEmail());
    }

}
