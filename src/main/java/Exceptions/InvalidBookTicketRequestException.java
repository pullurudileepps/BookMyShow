package Exceptions;

public class InvalidBookTicketRequestException extends Exception {
    public InvalidBookTicketRequestException(String message) {
        super(message);
    }
}
