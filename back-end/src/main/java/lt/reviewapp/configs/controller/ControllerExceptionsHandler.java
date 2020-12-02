package lt.reviewapp.configs.controller;

import lt.reviewapp.configs.controller.exceptions.BadRequestException;
import lt.reviewapp.models.ErrorDto;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ErrorDto handleException(EntityNotFoundException exception) {
        return new ErrorDto(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }
}
