package pl.pwr.SkillSwap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.SkillSwap.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
