package pl.pwr.SkillSwap.dto;

import lombok.Data;

@Data
public class AnnouncementPostRequest {
    private Long userId;
    private Long skillId;
    private String title;
    private String description;
    private String visibility;
}
