package nbcc.termproject.repositories;

import jakarta.transaction.Transactional;
import nbcc.termproject.entities.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {


    @Modifying
    @Transactional
    @Query("update Event e set e.startDate = :startDate, e.endDate = :endDate, " +
            "e.seatingDuration = :seatingDuration, e.name = :name, e.description = :description, " +
            "e.price = :price, e.menu.id = :menuId where e.id = :id")
    void updateEvent(@Param("id") int id,
                     @Param("startDate") LocalDate startDate,
                     @Param("endDate") LocalDate endDate,
                     @Param("seatingDuration") int seatingDuration,
                     @Param("name") String name,
                     @Param("description") String description,
                     @Param("price") double price,
                     @Param("menuId") int menuId);

}
