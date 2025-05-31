package pl.pwr.SkillSwap.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.pwr.SkillSwap.model.Rate;

import java.util.List;
import java.util.Optional;

public interface RateRepository extends JpaRepository<Rate, Long> {

    Page<Rate> findByOwnerId(Long ownerId, Pageable pageable);

    Optional<Rate> findBySenderIdAndOwnerId(Long senderId, Long ownerId);


    List<Rate> findByOwnerId(Long ownerId);

    @Query("SELECT AVG(r.value) FROM Rate r WHERE r.owner.id = :ownerId")
    Double findAverageByOwnerId(Long ownerId);

    Long countByOwnerId(Long ownerId);

}

