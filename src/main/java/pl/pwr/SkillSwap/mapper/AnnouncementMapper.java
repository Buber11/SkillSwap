package pl.pwr.SkillSwap.mapper;

import pl.pwr.SkillSwap.dto.AnnouncementGetRequest;
import pl.pwr.SkillSwap.model.Announcement;
import pl.pwr.SkillSwap.model.Skill;
import pl.pwr.SkillSwap.model.User;

public class AnnouncementMapper {

    public static AnnouncementGetRequest toDTO(Announcement entity) {
        if (entity == null) {
            return null;
        }

        AnnouncementGetRequest dto = new AnnouncementGetRequest();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setVisibility(entity.getVisibility());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        User user = entity.getUser();
        if (user != null) {
            dto.setUserId(user.getId());
            dto.setUsername(user.getUsername());
        }

        Skill skill = entity.getSkill();
        if (skill != null) {
            dto.setSkillId(skill.getId());
            dto.setSkillName(skill.getName());
        }

        return dto;
    }
}
