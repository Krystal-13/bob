package com.zerobase.bob.dto;

import lombok.Getter;

public class AuthRequestDto {

    @Getter
    public static class SignUp {
        private String name;
        private String email;
        private String password;
    }
    @Getter
    public static class SignIn {
        private String email;
        private String password;
    }



}
