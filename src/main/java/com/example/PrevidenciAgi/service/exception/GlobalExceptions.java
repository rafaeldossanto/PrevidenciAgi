package com.example.PrevidenciAgi.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(EmailInvalido.class)
    public ResponseEntity<String> handleEmailInvalido(EmailInvalido ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(EscolhaEnumInvalida.class)
    public ResponseEntity<String> handleEscolhaEnumInvalida(EscolhaEnumInvalida ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(JaExistente.class)
    public ResponseEntity<String> handleJaExistente(JaExistente ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(NaoEncontrado.class)
    public ResponseEntity<String> handleNaoEncontrado(NaoEncontrado ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(SenhaInvalida.class)
    public ResponseEntity<String> handleSenhaInvalida(SenhaInvalida ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(TempoInsuficiente.class)
    public ResponseEntity<String> handleTempoInsuficiente(TempoInsuficiente ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ValorInvalido.class)
    public ResponseEntity<String> handleValorInvalido(ValorInvalido ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
