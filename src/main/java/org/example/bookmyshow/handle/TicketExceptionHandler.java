package org.example.bookmyshow.handle;

import org.example.bookmyshow.Dtos.ErrorDto;
import org.example.bookmyshow.Exceptions.InvalidBookTicketRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TicketExceptionHandler {

    @ExceptionHandler(InvalidBookTicketRequestException.class)
    public ErrorDto handleInvalidBookTicketRequestException(InvalidBookTicketRequestException ex){
        return ErrorDto.builder()
                .status("FAIL")
                .errorMessage(ex.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
