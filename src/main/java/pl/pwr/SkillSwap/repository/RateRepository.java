package pl.pwr.SkillSwap.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.SkillSwap.model.Rate;

public interface RateRepository extends JpaRepository<Rate, Long> {

    Page<Rate> findByOwnerId(Long ownerId, Pageable pageable);
}

