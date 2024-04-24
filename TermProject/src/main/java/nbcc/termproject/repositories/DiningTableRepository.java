package nbcc.termproject.repositories;

import jakarta.transaction.Transactional;
import nbcc.termproject.entities.DiningTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiningTableRepository extends JpaRepository<DiningTable, Integer> {

    List<DiningTable> findAllByLayoutId(int layoutId);

    @Modifying
    @Transactional
    @Query("update DiningTable p set p.numberOfSeats = :numberOfSeats" +
            " where p.id = :id")
    void update(@Param("id") int id,
                @Param("numberOfSeats") int numberOfSeats);

}
