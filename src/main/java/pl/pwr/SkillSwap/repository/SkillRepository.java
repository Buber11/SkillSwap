package pl.pwr.SkillSwap.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.pwr.SkillSwap.model.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    @Query(
            value = "SELECT s.* FROM skill s INNER JOIN skills_user su ON s.id = su.skill_id WHERE su.user_id = :userId",
            countQuery = "SELECT COUNT(*) FROM skills_user su WHERE su.user_id = :userId",
            nativeQuery = true
    )
    Page<Skill> findByUser(@Param("userId") Long userId, Pageable pageable);

}


