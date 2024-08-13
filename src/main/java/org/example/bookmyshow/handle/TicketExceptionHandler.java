package org.example.bookmyshow.handle;

import org.example.bookmyshow.Dtos.ErrorDto;
import org.example.bookmyshow.Exceptions.InvalidBookTicketRequestException;
import org.example.bookmyshow.Exceptions.SeatsUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class TicketExceptionHandler {

    @ExceptionHandler(InvalidBookTicketRequestException.class)
    public ProblemDetail handleInvalidBookTicketRequestException(InvalidBookTicketRequestException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
    @ExceptionHandler(SeatsUnavailableException.class)
    public ProblemDetail handleSeatsUnavailableException(SeatsUnavailableException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
