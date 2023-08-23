package com.zerobase.bob.dto;

import lombok.Getter;

public class Auth {

    @Getter
    public static class SignUp {
        String name;
        String email;
        String password;
    }
    @Getter
    public static class SignIn {
        String email;
        String password;
    }



}
