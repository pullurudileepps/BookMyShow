package org.example.bookmyshow.Dtos;

import org.example.bookmyshow.Model.Ticket;
import lombok.Data;

@Data
public class BookTicketResponseDto {
    private Response response;
    private Ticket ticket;
}
