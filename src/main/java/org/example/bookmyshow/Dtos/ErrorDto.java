package org.example.bookmyshow.Dtos;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public class ErrorDto {
    private String status;
    private String errorMessage;
    private HttpStatus statusCode;
}
