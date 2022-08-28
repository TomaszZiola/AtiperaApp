package com.ziola.atiperaapp.errors;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private String message;

    public UserNotFoundException(String s) {
        super(s);
        this.message = s;
    }
}
