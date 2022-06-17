package ch.teachu.teachuapi.errorhandling;

import ch.teachu.teachuapi.dtos.ErrorResponse;
import ch.teachu.teachuapi.enums.LogLevel;
import ch.teachu.teachuapi.util.LogUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandling {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse exceptionHandler(Exception exception) {
        LogUtil.log(exception.getMessage(), exception.getStackTrace()[0].getClassName(), LogLevel.ERROR);
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exceptionHandler(InvalidException exception) {
        LogUtil.log(exception.getMessage(), exception.getStackTrace()[0].getClassName(), LogLevel.ERROR);
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse exceptionHandler(NotFoundException exception) {
        LogUtil.log(exception.getMessage(), exception.getStackTrace()[0].getClassName(), LogLevel.WARN);
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse exceptionHandler(UnauthorizedException exception) {
        LogUtil.log(exception.getMessage(), exception.getStackTrace()[0].getClassName(), LogLevel.INFO);
        return new ErrorResponse(exception.getMessage());
    }
}
