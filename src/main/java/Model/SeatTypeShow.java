package Model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "seat_type_shows")
public class SeatTypeShow extends BaseModel {
    private SeatType seatType;
    private Show show;
    private double price;
}
