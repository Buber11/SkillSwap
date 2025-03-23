package pl.pwr.SkillSwap.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import pl.pwr.SkillSwap.enums.VisibilityType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AnnouncementDTO {
    private Long id;
    private String title;
    private String description;
    private VisibilityType visibility;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long skillId;
    private Long userId;
    private String userName;
    private String userSurname;
}
