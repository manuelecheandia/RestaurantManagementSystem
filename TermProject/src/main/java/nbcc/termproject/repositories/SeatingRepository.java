package nbcc.termproject.repositories;

import jakarta.transaction.Transactional;
import nbcc.termproject.entities.Seating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SeatingRepository extends JpaRepository<Seating, Integer> {
    List<Seating> findAllByEventId(int eventId);

    @Query("SELECT s.event.id FROM Seating s WHERE s.id = :id")
    int selectEventBySeatingId (@Param("id") int id);


    @Modifying
    @Transactional
    @Query("update Seating s set s.startDateAndTime = :startDateAndTime" +
            " where s.id = :id")
    void update(@Param("id") int id,
                @Param("startDateAndTime") LocalDateTime startDateAndTime);
}
