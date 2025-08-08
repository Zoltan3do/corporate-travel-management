package manu_barone.ViaggiAziendali.exceptions;

import manu_barone.ViaggiAziendali.payloads.ErrorsRespDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsRespDTO handleBadRequest(BadRequestException ex){
        return new ErrorsRespDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsRespDTO handleNotFound(NotFoundException ex){
        return new ErrorsRespDTO(ex.getMessage(),LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsRespDTO handleGeneric(Exception ex){
        ex.printStackTrace();
        return new ErrorsRespDTO("Problema lato server",LocalDateTime.now());
    }
}
