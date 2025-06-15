package pl.pwr.SkillSwap.service;

import org.springframework.stereotype.Service;
import pl.pwr.SkillSwap.dto.MessageResponseDTO;
import pl.pwr.SkillSwap.dto.UserDTO;
import pl.pwr.SkillSwap.model.Chat;
import pl.pwr.SkillSwap.model.Message;
import pl.pwr.SkillSwap.model.User;
import pl.pwr.SkillSwap.repository.ChatRepository;
import pl.pwr.SkillSwap.repository.MessageRepository;
import pl.pwr.SkillSwap.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatService chatService;
    private final ChatRepository chatRepository; 

    public MessageService(MessageRepository messageRepository,
                          UserRepository userRepository,
                          ChatService chatService,
                          ChatRepository chatRepository) { 
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.chatService = chatService;
        this.chatRepository = chatRepository; 
    }

    public boolean hasUserContact(Long senderId, Long receiverId) {
        Long firstId = Math.min(senderId, receiverId);
        Long secondId = Math.max(senderId, receiverId);

        Optional<Chat> chatOpt = chatRepository.findChatBetweenUsers(firstId, secondId);
        if (chatOpt.isEmpty()) {
            return false;
        }

        Chat chat = chatOpt.get();
        List<Message> messages = messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
        boolean hasSentMessage = messages.stream()
            .anyMatch(message -> message.getSender().getId().equals(senderId));
        return hasSentMessage;
    }

    public Message sendMessage(Long senderId, Long receiverId, String text) {
        Chat chat = chatService.getOrCreateChat(senderId, receiverId);

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Message newMessage = new Message();
        newMessage.setSender(sender);
        newMessage.setChat(chat);
        newMessage.setText(text);
        newMessage.setType(pl.pwr.SkillSwap.enums.MessageType.TEXT);

        return messageRepository.save(newMessage);
    }

    public List<Message> getMessagesBetween(Long senderId, Long receiverId) {
        Chat chat = chatService.getOrCreateChat(senderId, receiverId);
        return messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
    }

    public MessageResponseDTO convertToDTO(Message message) {
        MessageResponseDTO dto = new MessageResponseDTO();
        dto.setId(message.getId());
        dto.setType(message.getType().toString());
        dto.setChatId(message.getChat().getId());
        dto.setText(message.getText());
        dto.setCreatedAt(message.getCreatedAt());
        User sender = message.getSender();
        if (sender != null) {
            UserDTO senderDTO = new UserDTO(sender.getId(), sender.getUsername(), sender.getEmail());
            dto.setSender(senderDTO);
        }
        return dto;
    }

    public List<MessageResponseDTO> getMessagesBetweenDTO(Long senderId, Long receiverId) {
        List<Message> messages = getMessagesBetween(senderId, receiverId);
        return messages.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
