package pl.pwr.SkillSwap.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.pwr.SkillSwap.model.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query(
            value = "SELECT a FROM Announcement a " +
                    "JOIN FETCH a.user u " +
                    "LEFT JOIN FETCH a.skill s",
            countQuery = "SELECT count(a) FROM Announcement a"
    )
    Page<Announcement> findAllWithUserAndSkill(Pageable pageable);
}


