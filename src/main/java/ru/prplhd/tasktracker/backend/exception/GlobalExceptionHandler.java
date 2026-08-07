package ru.prplhd.tasktracker.backend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.prplhd.tasktracker.backend.dto.ErrorResponseDto;
import ru.prplhd.tasktracker.backend.dto.ValidationErrorResponseDto;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        ErrorResponseDto response = new ErrorResponseDto("Invalid value for '%s'".formatted(e.getName()));

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleMessageNotReadableException() {
        ErrorResponseDto response = new ErrorResponseDto("Request body contains an invalid value");

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> validationErrors = new ArrayList<>();

        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            validationErrors.add(error.getDefaultMessage());
        }

        ValidationErrorResponseDto validationErrorResponseDto = new ValidationErrorResponseDto(validationErrors);

        return new ResponseEntity<>(validationErrorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getMessage());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidCredentialsException(InvalidCredentialsException e) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getMessage());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }
}
