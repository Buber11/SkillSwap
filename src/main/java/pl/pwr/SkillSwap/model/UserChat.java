package pl.pwr.SkillSwap.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_chat")
public class UserChat {

    @EmbeddedId
    private UserChatId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("chatId")
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public UserChat() {
    }

    public UserChat(User user, Chat chat) {
        this.user = user;
        this.chat = chat;
        this.id = new UserChatId(user.getId(), chat.getId());
    }
}

