package pl.pwr.SkillSwap.dto;

import lombok.Data;
import pl.pwr.SkillSwap.enums.VisibilityType;

import java.time.LocalDateTime;

@Data
public class AnnouncementGetRequest {
    private Long id;
    private String title;
    private String description;
    private VisibilityType visibility;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long userId;
    private String username;
    private Long skillId;
    private String skillName;
}
