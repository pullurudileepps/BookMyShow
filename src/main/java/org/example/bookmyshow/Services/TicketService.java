package org.example.bookmyshow.Services;

import org.example.bookmyshow.Model.Ticket;

import java.util.List;

public interface TicketService {
    Ticket bookTicket(int userId, int showId, List<Integer> showSeatIds) throws Exception;
}
