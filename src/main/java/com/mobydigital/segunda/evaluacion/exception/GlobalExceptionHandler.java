package com.mobydigital.segunda.evaluacion.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, Object>> buildError(HttpStatus status, String error, String message) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("mensaje", message);

        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(CandidateNotExistException.class)
    public ResponseEntity<Map<String, Object>> handlerCandidateNotFound(CandidateNotExistException ex) {
        return buildError(HttpStatus.NOT_FOUND, "Candidato no encontrado", ex.getMessage());
    }

    @ExceptionHandler(PoliticalPartyNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerPoliticalPartyNotFound(PoliticalPartyNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, "Partido político no encontrado", ex.getMessage());
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Map<String, Object>> handlerInvalidData(InvalidDataException ex) {
        return buildError(HttpStatus.BAD_REQUEST, "Datos inválidos", ex.getMessage());
    }


}
