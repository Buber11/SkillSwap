package pl.pwr.SkillSwap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.SkillSwap.model.Rate;

public interface RateRepository extends JpaRepository<Rate, Long> {
}

