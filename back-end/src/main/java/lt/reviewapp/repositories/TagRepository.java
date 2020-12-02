package lt.reviewapp.repositories;

import lt.reviewapp.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    @Modifying
    @Query("update Tag t set t.title = :title where t.id = :id")
    void updateTitleById(@Param("id") Integer id, @Param("title") String title);

    boolean existsByTitleIgnoreCase(String title);
}
