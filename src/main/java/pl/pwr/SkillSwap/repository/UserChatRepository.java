package pl.pwr.SkillSwap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwr.SkillSwap.model.UserChat;

public interface UserChatRepository extends JpaRepository<UserChat, Long> {
    // dodatkowe metody, jeśli będą potrzebne
}
