package pl.pwr.SkillSwap.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.pwr.SkillSwap.dto.AnnouncementDTO;
import pl.pwr.SkillSwap.model.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    @Query(value = """
    SELECT new pl.pwr.SkillSwap.dto.AnnouncementDTO(
        a.id, a.title, a.description, a.visibility,
        a.createdAt, a.updatedAt,
        a.skill.id, u.id, ud.name, ud.surname
    )
    FROM Announcement a
    JOIN a.user u
    JOIN UserDetails ud ON ud.user.id = u.id
    """)
    Page<AnnouncementDTO> findAllWithUserDetails(Pageable pageable);

}
