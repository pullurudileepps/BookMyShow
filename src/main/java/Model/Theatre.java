package Model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import java.util.List;

@Data
@Entity(name = "theatres")
public class Theatre extends BaseModel {
    private String name;
    @ManyToOne
    private City city;
    @OneToMany(mappedBy = "theatre")
    private List<Screen> screens;
}
