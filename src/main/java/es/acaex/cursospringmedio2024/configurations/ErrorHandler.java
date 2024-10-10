package es.acaex.cursospringmedio2024.configurations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import es.acaex.cursospringmedio2024.dto.ErrorObject;

import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorHandler {

   // Manejo de Excepcion Campos no validos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorObject> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ErrorObject error = new ErrorObject();
        error.setCode(0);
        error.setError(errors);
        return ResponseEntity.badRequest().body(error);
    }


// Excepcion de parse de JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Map<String, String> errors = new HashMap<>();

        // Aquí puedes obtener información sobre la causa
        Throwable cause = ex.getCause();
        if (cause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException) {
            com.fasterxml.jackson.databind.exc.InvalidFormatException invalidFormatException = (com.fasterxml.jackson.databind.exc.InvalidFormatException) cause;

            // Obtener el nombre del campo que causó el error
            String fieldName = invalidFormatException.getPath().get(0).getFieldName();
            errors.put(fieldName, "Formato inválido para el campo: " + fieldName);
        } else {
            errors.put("error", "Error en la solicitud: " + ex.getMessage());
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}