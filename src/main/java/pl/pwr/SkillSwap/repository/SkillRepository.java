package pl.pwr.SkillSwap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.SkillSwap.model.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
}

