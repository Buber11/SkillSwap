package pl.pwr.SkillSwap.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.SkillSwap.enums.ChatStatus;
import pl.pwr.SkillSwap.model.Chat;
import pl.pwr.SkillSwap.model.User;
import pl.pwr.SkillSwap.model.UserChat;
import pl.pwr.SkillSwap.repository.ChatRepository;
import pl.pwr.SkillSwap.repository.UserChatRepository;
import pl.pwr.SkillSwap.repository.UserRepository;
import java.util.Optional;

@Service
@Transactional
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserChatRepository userChatRepository;
    private final UserRepository userRepository;

    public ChatService(ChatRepository chatRepository, 
                       UserChatRepository userChatRepository,
                       UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userChatRepository = userChatRepository;
        this.userRepository = userRepository;
    }

    public Chat getOrCreateChat(Long userId1, Long userId2) {
        Long firstId = Math.min(userId1, userId2);
        Long secondId = Math.max(userId1, userId2);

        Optional<Chat> chatOpt = chatRepository.findChatBetweenUsers(firstId, secondId);
        if (chatOpt.isPresent()) {
            return chatOpt.get();
        } else {
            Chat newChat = new Chat();
            newChat.setStatus(ChatStatus.ACTIVE);
            chatRepository.save(newChat);

            User user1 = userRepository.findById(firstId)
                    .orElseThrow(() -> new RuntimeException("User with id " + firstId + " not found"));
            User user2 = userRepository.findById(secondId)
                    .orElseThrow(() -> new RuntimeException("User with id " + secondId + " not found"));

            UserChat uc1 = new UserChat(user1, newChat);
            UserChat uc2 = new UserChat(user2, newChat);

            userChatRepository.save(uc1);
            userChatRepository.save(uc2);

            return newChat;
        }
    }

}
