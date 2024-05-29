package Services;

import Model.Ticket;

import java.sql.SQLException;
import java.util.List;

public interface TicketService {
    Ticket bookTicket(int userId, int showId, List<Integer> showSeatIds) throws Exception;
}
