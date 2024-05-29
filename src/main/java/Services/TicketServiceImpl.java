package Services;

import Exceptions.InvalidBookTicketRequestException;
import Exceptions.SeatsUnavailableException;
import Model.*;
import Repositories.ShowRepository;
import Repositories.ShowSeatResitory;
import Repositories.TicketRepository;
import Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatResitory showSeatResitory;
    private TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImpl(UserRepository userRepository, ShowRepository showRepository, ShowSeatResitory showSeatResitory, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatResitory = showSeatResitory;
        this.ticketRepository = ticketRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public Ticket bookTicket(int userId, int showId, List<Integer> showSeatIds) throws Exception {
        Optional<User> userOptional = this.userRepository.findById(userId);
        User user;
        if (userOptional.isPresent())
            user = userOptional.get();
        else
            throw new InvalidBookTicketRequestException("User id is invalid");
        Show show = this.showRepository.findById(showId).orElseThrow(() -> new InvalidBookTicketRequestException("Show id is invalid"));
        ShowSeat showSeat = this.showSeatResitory.findById(showSeatIds.getFirst()).orElseThrow(() -> new InvalidBookTicketRequestException("Seat id is invalid"));
        if (showSeat.getShow().getId() != showId) {
            throw new InvalidBookTicketRequestException("Given seats does not belong to the same show");
        }
        List<ShowSeat> showSeats = this.showSeatResitory.findShowSeatsByIdInAndSeatStatus_AvailableAndShow(showSeatIds, show);
        if (showSeats.size() != showSeatIds.size()) {
            throw new SeatsUnavailableException("some of the seats you are trying to book are unavailable");
        }
        for (ShowSeat SS : showSeats) {
            SS.setSeatStatus(SeatStatus.BLOCKED);
            SS.setBookedBy(user);
        }
        this.showSeatResitory.saveAll(showSeats);
        Ticket ticket = new Ticket();
        ticket.setMovie(show.getMovie());
        ticket.setShow(show);
        ticket.setUser(user);
        ticket.setShowSeats(showSeats);
        return this.ticketRepository.save(ticket);
    }
}
