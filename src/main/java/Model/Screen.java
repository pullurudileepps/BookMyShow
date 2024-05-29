package Model;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.*;

@Data
@Entity(name = "screens")
public class Screen extends BaseModel {
    private String name;
    @ManyToOne
    private Theatre theatre;
    @OneToMany(mappedBy = "screen")
    private List<Seat> seats;
}
