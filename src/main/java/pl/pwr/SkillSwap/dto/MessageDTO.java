package pl.pwr.SkillSwap.dto;

public class MessageDTO {
    private Long senderId;
    private Long receiverId;
    private String text;

    public MessageDTO() {}

    public MessageDTO(Long senderId, Long receiverId, String text) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
    }

    // Gettery i settery
    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
