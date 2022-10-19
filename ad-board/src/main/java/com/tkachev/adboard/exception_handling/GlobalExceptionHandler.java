package com.tkachev.adboard.exception_handling;

import com.tkachev.adboard.exception_handling.exceptions.EntityAlreadyExistsException;
import com.tkachev.adboard.exception_handling.exceptions.JwtAuthenficationException;
import com.tkachev.adboard.exception_handling.exceptions.NoSuchEntityException;
import com.tkachev.adboard.exception_handling.exceptions.NoSuchUserInChatException;
import com.tkachev.adboard.exception_handling.exceptions.ParametrException;
import com.tkachev.adboard.exception_handling.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoSuchEntityException.class, UsernameNotFoundException.class})
    public ResponseEntity<ExceptionResponse> handleNoSuchEntityException(Exception exception) {
        ExceptionResponse data = new ExceptionResponse();
        data.setExceptionMessage(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EntityAlreadyExistsException.class})
    public ResponseEntity<ExceptionResponse> handleEntityAlreadyExistsException(Exception exception) {
        ExceptionResponse data = new ExceptionResponse();
        data.setExceptionMessage(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({NoSuchUserInChatException.class})
    public ResponseEntity<ExceptionResponse> handleNoSuchUserInChatException(Exception exception) {
        ExceptionResponse data = new ExceptionResponse();
        data.setExceptionMessage(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JwtAuthenficationException.class)
    public ResponseEntity<ExceptionResponse> handleJwtAuthenticationException(JwtAuthenficationException exception) {
        ExceptionResponse data = new ExceptionResponse();
        data.setExceptionMessage(exception.getMessage());

        return new ResponseEntity<>(data, exception.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String name = ex.getName();
        String type = Objects.requireNonNull(ex.getRequiredType()).getSimpleName();
        Object value = ex.getValue();
        String message = String.format("Parameter '%s' should be a valid '%s' and '%s' isn't",
                name, type, value);

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({ParametrException.class})
    public ResponseEntity<ExceptionResponse> handleException(Exception exception) {
        ExceptionResponse data = new ExceptionResponse();
        data.setExceptionMessage(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
