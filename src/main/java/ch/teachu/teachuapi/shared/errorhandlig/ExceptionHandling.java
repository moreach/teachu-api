package ch.teachu.teachuapi.shared.errorhandlig;

import ch.teachu.teachuapi.shared.dtos.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandling {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandling.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse exceptionHandler(Exception exception) {
        LOG.error(exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exceptionHandler(InvalidException exception) {
        LOG.error(exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse exceptionHandler(NotFoundException exception) {
        LOG.warn(exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse exceptionHandler(UnauthorizedException exception) {
        LOG.info(exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage());
    }
}
