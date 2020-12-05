package lt.reviewapp.configs.controller;

import lt.reviewapp.configs.controller.exceptions.BadRequestException;
import lt.reviewapp.models.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ControllerExceptionsHandler {
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorDto handleException(BadRequestException exception) {
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorDto handleException(MethodArgumentNotValidException exception) {
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), "Invalid request body.");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorDto handleException(BadCredentialsException exception) {
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), "Incorrect credentials.");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler
    public ErrorDto handleException(AccessDeniedException exception) {
        return new ErrorDto(HttpStatus.FORBIDDEN.value(), exception.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ErrorDto handleException(EntityNotFoundException exception) {
        return new ErrorDto(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }
}
