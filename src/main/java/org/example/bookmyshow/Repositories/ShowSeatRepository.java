package org.example.bookmyshow.Repositories;
import org.example.bookmyshow.Model.SeatStatus;
import org.example.bookmyshow.Model.Show;
import org.example.bookmyshow.Model.ShowSeat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Integer> {
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    List<ShowSeat> findShowSeatsByIdInAndSeatStatusAndShow(List<Integer> showSeatIds, SeatStatus seatStatus, Show show);
}
