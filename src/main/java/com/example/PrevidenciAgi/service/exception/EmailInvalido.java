package com.example.PrevidenciAgi.service.exception;

public class EmailInvalido extends RuntimeException {
    public EmailInvalido(String message) {
        super(message);
    }
}
