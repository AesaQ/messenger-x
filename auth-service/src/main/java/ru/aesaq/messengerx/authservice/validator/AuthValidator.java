package ru.aesaq.messengerx.authservice.validator;

import org.springframework.stereotype.Component;

@Component
public class AuthValidator {

    public String validateUsername(String username) {
        if (username.isEmpty()) {
            return "username cant be empty";
        }
        if (username.length() > 20) {
            return "there are too many characters in the username";
        }
        return "ok";
    }

    public String validatePassword(String password) {
        if (password.isEmpty()) {
            return "password cant be empty";
        }
        if (password.length() < 7) {
            return "password is too small";
        }
//        if (!Pattern.compile("\\d").matcher(password).find()) {
//            return "the password must contain at least 1 digit";
//        }
        return "ok";
    }
}
