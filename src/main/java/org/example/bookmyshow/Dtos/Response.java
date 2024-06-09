package org.example.bookmyshow.Dtos;

import lombok.Data;

@Data
public class Response {
    private ResponseStatus status;
    private String error;

    public Response(ResponseStatus status, String error) {
        this.status = status;
        this.error = error;
    }

    public static Response getFailedResponse(String error) {
        return new Response(ResponseStatus.FAILED, error);
    }

    public static Response getSuccessResponse() {
        return new Response(ResponseStatus.SUCCESS, null);
    }
}
