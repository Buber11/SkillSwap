package pl.pwr.SkillSwap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.SkillSwap.model.UserDetails;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
    Optional<UserDetails> findByUserId(Long userId);

    Optional<UserDetails> findByUserUsername(String username);
}
