package pl.pwr.SkillSwap.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pwr.SkillSwap.dto.MessageResponseDTO;
import pl.pwr.SkillSwap.dto.MessageDTO;
import pl.pwr.SkillSwap.model.Chat;
import pl.pwr.SkillSwap.model.Message;
import pl.pwr.SkillSwap.service.ChatService;
import pl.pwr.SkillSwap.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;
    private final MessageService messageService;

    public ChatController(ChatService chatService, MessageService messageService) {
        this.chatService = chatService;
        this.messageService = messageService;
    }

    @PostMapping("/getOrCreate")
    public ResponseEntity<Chat> getOrCreateChat(@RequestParam Long userId1,
                                                @RequestParam Long userId2) {
        Chat chat = chatService.getOrCreateChat(userId1, userId2);
        return ResponseEntity.ok(chat);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<MessageResponseDTO>> getChatMessages(
            @RequestParam Long senderId,
            @RequestParam Long receiverId) {
        List<MessageResponseDTO> messagesDTO = messageService.getMessagesBetweenDTO(senderId, receiverId);
        return ResponseEntity.ok(messagesDTO);
    }

    @PostMapping("/messages")
    public ResponseEntity<MessageResponseDTO> sendMessage(@RequestBody pl.pwr.SkillSwap.dto.MessageDTO messageDTO) {
        Message savedMessage = messageService.sendMessage(
                messageDTO.getSenderId(),
                messageDTO.getReceiverId(),
                messageDTO.getText());
        MessageResponseDTO dto = messageService.convertToDTO(savedMessage);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/hasConversation")
    public ResponseEntity<Boolean> hasConversation(@RequestParam Long senderId,
                                                    @RequestParam Long receiverId) {
        boolean hasConversation = messageService.hasUserContact(senderId, receiverId);
        return ResponseEntity.ok(hasConversation);
    }

}
