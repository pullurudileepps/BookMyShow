package org.example.bookmyshow.Model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "seats")
public class Seat extends BaseModel{
    private String name;
    private SeatType seatType;
}
