package org.example.bookmyshow.Services;

import org.example.bookmyshow.Exceptions.InvalidBookTicketRequestException;
import org.example.bookmyshow.Exceptions.SeatsUnavailableException;
import org.example.bookmyshow.Repositories.ShowRepository;
import org.example.bookmyshow.Repositories.ShowSeatRepository;
import org.example.bookmyshow.Repositories.TicketRepository;
import org.example.bookmyshow.Repositories.UserRepository;
import org.example.bookmyshow.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImpl(UserRepository userRepository, ShowRepository showRepository, ShowSeatRepository showSeatRepository, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
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
        ShowSeat showSeat = this.showSeatRepository.findById(showSeatIds.getFirst()).orElseThrow(() -> new InvalidBookTicketRequestException("Seat id is invalid"));
        if (showSeat.getShow().getId() != showId) {
            throw new InvalidBookTicketRequestException("Given seats does not belong to the same show");
        }
        List<ShowSeat> showSeats = this.showSeatRepository.findShowSeatsByIdInAndSeatStatusAndShow(showSeatIds, SeatStatus.AVAILABLE, show);
        if (showSeats.size() != showSeatIds.size()) {
            throw new SeatsUnavailableException("some of the seats you are trying to book are unavailable");
        }
        for (ShowSeat SS : showSeats) {
            SS.setSeatStatus(SeatStatus.BLOCKED);
            SS.setBookedBy(user);
        }
        this.showSeatRepository.saveAll(showSeats);
        Ticket ticket = new Ticket();
        ticket.setMovie(show.getMovie());
        ticket.setShow(show);
        ticket.setUser(user);
        ticket.setShowSeats(showSeats);
        return this.ticketRepository.save(ticket);
    }
}
