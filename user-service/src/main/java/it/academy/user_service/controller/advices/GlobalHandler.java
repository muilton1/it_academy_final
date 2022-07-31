package it.academy.user_service.controller.advices;


import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;


@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object onConstraintValidationException(
            ConstraintViolationException e) {
        ErrorResponse error = new ErrorResponse();
        error.setLogref("structured_error");
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            error.getErrorMessages().add(
                    new ErrorMessage(violation.getPropertyPath().toString().
                            substring(violation.getPropertyPath().toString().lastIndexOf(".") + 1), violation.getMessage()));
        }
        List<ErrorMessage> errorMessages = error.getErrorMessages();

        if (errorMessages.size() > 1) {
            return error;
        } else {
            return errorMessages.get(0);
        }
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage onIllegalArgumentException(
            IllegalArgumentException e) {
        return new ErrorMessage("error", e.getLocalizedMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage onHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new ErrorMessage("error", e.getLocalizedMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage onClassCastException(ClassCastException e) {
        return new ErrorMessage("error", "Сервер не смог корректно обработать запрос. Пожалуйста обратитесь к администратору");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage onEntityNotFoundException(EntityNotFoundException e) {
        return new ErrorMessage("error", e.getLocalizedMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage onJSON(JsonParseException e) {
        return new ErrorMessage("error", "Сервер не смог корректно обработать запрос. Пожалуйста обратитесь к администратору");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage onJSON(com.fasterxml.jackson.core.JsonParseException e) {
        return new ErrorMessage("error", "Сервер не смог корректно обработать запрос. Пожалуйста обратитесь к администратору");
    }
}

