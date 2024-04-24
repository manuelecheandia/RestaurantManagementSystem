package nbcc.termproject.repositories;

import jakarta.transaction.Transactional;
import nbcc.termproject.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
;
import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
    List<MenuItem> findAllByMenuId(int menuId);

    @Modifying
    @Transactional
    @Query("update MenuItem mi set mi.name = :name, mi.description = :description where mi.id = :id")
    void update(@Param("id") int id,
                @Param("name") String name,
                @Param("description") String description);
}
