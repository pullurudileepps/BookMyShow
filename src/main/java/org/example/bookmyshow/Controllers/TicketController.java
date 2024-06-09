package org.example.bookmyshow.Controllers;

import org.example.bookmyshow.Dtos.BookTicketRequestDto;
import org.example.bookmyshow.Dtos.BookTicketResponseDto;
import org.example.bookmyshow.Dtos.Response;
import org.example.bookmyshow.Exceptions.InvalidBookTicketRequestException;
import org.example.bookmyshow.Model.Ticket;
import org.example.bookmyshow.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @RequestMapping(path = "/bookTicket")
    public BookTicketResponseDto bookTicket(BookTicketRequestDto requestDto) {
        BookTicketResponseDto responseDto = new BookTicketResponseDto();
        try {
            validationBookTicketRequest(requestDto);
            Ticket ticket = ticketService.bookTicket(requestDto.getUserId(), requestDto.getShowId(), requestDto.getShowSeatIds());
            Response response = Response.getSuccessResponse();
            responseDto.setTicket(ticket);
            responseDto.setResponse(response);
        } catch (Exception e) {
            Response response = Response.getFailedResponse(e.getMessage());
            responseDto.setResponse(response);
        }
        return responseDto;
    }

    private static void validationBookTicketRequest(BookTicketRequestDto requestDto) throws InvalidBookTicketRequestException {
        if(requestDto.getUserId() <= 0){
            throw new InvalidBookTicketRequestException("User id can be negative or zero");
        }
        if(requestDto.getShowId() <= 0){
            throw new InvalidBookTicketRequestException("Show id can be negative or zero");
        }
        if(requestDto.getShowSeatIds() == null || requestDto.getShowSeatIds().isEmpty()){
            throw new InvalidBookTicketRequestException("showSeatIds should be present");
        }
    }
}
