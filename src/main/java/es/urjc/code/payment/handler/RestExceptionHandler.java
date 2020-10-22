package es.urjc.code.payment.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import es.urjc.code.payment.exception.BusinessException;
import es.urjc.code.payment.exception.CommunicationException;
import es.urjc.code.payment.exception.EntityNotFoundException;
import es.urjc.code.payment.exception.NotAvailableException;

@RestControllerAdvice
public class RestExceptionHandler
{
    @ExceptionHandler(value = { IOException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequest(Exception ex)
    {
        return new ErrorResponse.Builder()
        		                .withStatus(400)
        		                .withMessage("Bad Request")
        		                .build();
    }
    
    @ExceptionHandler(value = { EntityNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse unKnownException(Exception ex)
    {
        return new ErrorResponse.Builder()
                .withStatus(404)
                .withMessage(ex.getMessage())
                .build();
    }
    
    @ExceptionHandler(value = { BusinessException.class })
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse businessException(Exception ex)
    {
        return new ErrorResponse.Builder()
                .withStatus(422)
                .withMessage(ex.getMessage())
                .build();
    }
    
    @ExceptionHandler(value = { NotAvailableException.class })
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponse notAvailableException(Exception ex)
    {
        return new ErrorResponse.Builder()
                .withStatus(503)
                .withMessage(ex.getMessage())
                .build();
    }
    
    @ExceptionHandler(value = { CommunicationException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse communicationException(Exception ex)
    {
        return new ErrorResponse.Builder()
                .withStatus(500)
                .withMessage(ex.getMessage())
                .build();
    }
    
}