package nbcc.termproject.repositories;

import jakarta.transaction.Transactional;
import nbcc.termproject.entities.Layout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LayoutRepository extends JpaRepository<Layout, Integer> {

    List<Layout> findAllByEvents_Id(int eventId);

    @Modifying
    @Transactional
    @Query("update Layout p set p.name = :name, p.description = :description" +
            " where p.id = :id")
    void update(@Param("id") int id,
                @Param("name") String name,
                @Param("description") String description);
}
