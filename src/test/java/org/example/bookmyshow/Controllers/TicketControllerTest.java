package org.example.bookmyshow.Controllers;

import org.example.bookmyshow.Dtos.BookTicketRequestDto;
import org.example.bookmyshow.Dtos.BookTicketResponseDto;
import org.example.bookmyshow.Dtos.Response;
import org.example.bookmyshow.Dtos.ResponseStatus;
import org.example.bookmyshow.Model.Ticket;
import org.example.bookmyshow.Services.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
class TicketControllerTest {

    @Autowired
    private TicketController ticketController;

    @MockBean
    private TicketService ticketService;

    @Test
    public void TestUserId_Failure(){
        //Arrange
        BookTicketRequestDto requestDto = new BookTicketRequestDto();
        requestDto.setUserId(-1);
        requestDto.setShowId(1);
        requestDto.setShowSeatIds(List.of(1,2,3));

        //Act
        BookTicketResponseDto responseDto = ticketController.bookTicket(requestDto);

        //Assert
        assertNotNull(responseDto, "Response dto should not be null");
        assertNotNull(responseDto.getResponse());
        assertEquals(ResponseStatus.FAILED, responseDto.getResponse().getStatus());
        assertNotNull(responseDto.getResponse().getError());
        assertNull(responseDto.getTicket(), "Ticket should not be generated");
    }

    @Test
    public void TestShowId_Failure(){
        //Arrange
        BookTicketRequestDto requestDto = new BookTicketRequestDto();
        requestDto.setUserId(1);
        requestDto.setShowId(-1);
        requestDto.setShowSeatIds(List.of(1,2,3));

        //Act
        BookTicketResponseDto responseDto = ticketController.bookTicket(requestDto);

        //Assert
        assertNotNull(responseDto, "Response dto should not be null");
        assertNotNull(responseDto.getResponse());
        assertEquals(ResponseStatus.FAILED, responseDto.getResponse().getStatus());
        assertNotNull(responseDto.getResponse().getError());
        assertNull(responseDto.getTicket(), "Ticket should not be generated");
    }
    @Test
    public void TestShowSeatIds_Failure(){
        //Arrange
        BookTicketRequestDto requestDto = new BookTicketRequestDto();
        requestDto.setUserId(1);
        requestDto.setShowId(1);
        requestDto.setShowSeatIds(List.of());

        //Act
        BookTicketResponseDto responseDto = ticketController.bookTicket(requestDto);

        //Assert
        assertNotNull(responseDto, "Response dto should not be null");
        assertNotNull(responseDto.getResponse());
        assertEquals(ResponseStatus.FAILED, responseDto.getResponse().getStatus());
        assertNotNull(responseDto.getResponse().getError());
        assertNull(responseDto.getTicket(), "Ticket should not be generated");
    }
    @Test
    public void TestShowSeatIds1_Failure(){
        //Arrange
        BookTicketRequestDto requestDto = new BookTicketRequestDto();
        requestDto.setUserId(1);
        requestDto.setShowId(1);

        //Act
        BookTicketResponseDto responseDto = ticketController.bookTicket(requestDto);

        //Assert
        assertNotNull(responseDto, "Response dto should not be null");
        assertNotNull(responseDto.getResponse());
        assertEquals(ResponseStatus.FAILED, responseDto.getResponse().getStatus());
        assertNotNull(responseDto.getResponse().getError());
        assertNull(responseDto.getTicket(), "Ticket should not be generated");
    }
    @Test
    public void TestBookTicket_Failure() throws Exception {
        //Arrange
        BookTicketRequestDto requestDto = new BookTicketRequestDto();
        requestDto.setUserId(1);
        requestDto.setShowId(1);
        requestDto.setShowSeatIds(List.of(1,2,3));

        when(this.ticketService.bookTicket(requestDto.getUserId(),
                requestDto.getShowId(), requestDto.getShowSeatIds())).thenThrow(Exception.class);

        //Act
        BookTicketResponseDto responseDto = ticketController.bookTicket(requestDto);

        //Assert
        assertNotNull(responseDto, "Response dto should not be null");
        assertNotNull(responseDto.getResponse());
        assertNull(responseDto.getResponse().getError());
        assertEquals(ResponseStatus.FAILED, responseDto.getResponse().getStatus());
        assertNull(responseDto.getTicket(), "Ticket should not be generated");
    }
    @Test
    public void TestBookTicket_Success() throws Exception {
        //Arrange
        BookTicketRequestDto requestDto = new BookTicketRequestDto();
        requestDto.setUserId(1);
        requestDto.setShowId(1);
        requestDto.setShowSeatIds(List.of(1,2,3));

        when(this.ticketService.bookTicket(requestDto.getUserId(),
                requestDto.getShowId(), requestDto.getShowSeatIds())).thenReturn(new Ticket());

        //Act
        BookTicketResponseDto responseDto = ticketController.bookTicket(requestDto);

        //Assert
        assertNotNull(responseDto, "Response dto should not be null");
        assertNotNull(responseDto.getResponse());
        assertNull(responseDto.getResponse().getError());
        assertEquals(ResponseStatus.SUCCESS, responseDto.getResponse().getStatus());
        assertNotNull(responseDto.getTicket(), "Ticket should be generated");
    }
}