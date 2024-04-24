package nbcc.termproject.repositories;


import nbcc.termproject.entities.Event;
import nbcc.termproject.entities.Reservation;
import nbcc.termproject.entities.Seating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findAllBySeating(Seating seating);
}
