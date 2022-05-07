package ch.teachu.teachuapi.errorhandling;

import ch.teachu.teachuapi.dtos.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.webjars.NotFoundException;

@ControllerAdvice
public class ExceptionHandling {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO exceptionHandler(Exception exception) {
        return new ErrorDTO(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO exceptionHandler(InvalidException exception) {
        return new ErrorDTO(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO exceptionHandler(NotFoundException exception) {
        return new ErrorDTO(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO exceptionHandler(UnauthorizedException exception) {
        return new ErrorDTO(exception.getMessage());
    }
}
