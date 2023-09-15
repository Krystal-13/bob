package com.zerobase.bob.service;

import com.zerobase.bob.dto.AuthRequestDto;
import com.zerobase.bob.entity.User;
import com.zerobase.bob.exception.CustomException;
import com.zerobase.bob.repository.UserRepository;
import com.zerobase.bob.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.zerobase.bob.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public boolean signUp(AuthRequestDto.SignUp request) {

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

    public String signIn(AuthRequestDto.SignIn request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (!this.passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(UNMATCHED_INFORMATION);
        }

        return tokenProvider
                .generateToken(user.getEmail());
    }

}
