package pl.pwr.SkillSwap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.pwr.SkillSwap.model.Chat;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c JOIN c.userChats uc1 JOIN c.userChats uc2 " +
           "WHERE uc1.user.id = :userId1 AND uc2.user.id = :userId2")
    Optional<Chat> findChatBetweenUsers(@Param("userId1") Long userId1,
                                        @Param("userId2") Long userId2);
}
