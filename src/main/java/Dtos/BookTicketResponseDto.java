package Dtos;

import Model.Ticket;
import lombok.Data;

@Data
public class BookTicketResponseDto {
    private Response response;
    private Ticket ticket;
}
