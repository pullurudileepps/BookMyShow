package Model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "seat")
public class Seat extends BaseModel {
    private String name;
    private SeatType seatType;
    @ManyToOne
    private Screen screen;
}
