package nbcc.termproject.repositories;

import jakarta.transaction.Transactional;
import nbcc.termproject.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {


    @Modifying
    @Transactional
    @Query("update Menu m set m.name = :name, m.description = :description where m.id = :id")
    void updateMenu(@Param("id") int id,
                     @Param("name") String name,
                     @Param("description") String description);

}
