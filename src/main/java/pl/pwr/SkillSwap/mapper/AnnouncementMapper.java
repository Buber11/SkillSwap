package pl.pwr.SkillSwap.mapper;

import org.springframework.stereotype.Component;
import pl.pwr.SkillSwap.dto.AnnouncementDTO;
import pl.pwr.SkillSwap.model.Announcement;

@Component
public class AnnouncementMapper {

    public AnnouncementDTO toDTO(Announcement announcement) {
        AnnouncementDTO dto = new AnnouncementDTO();
        dto.setId(announcement.getId());
        dto.setTitle(announcement.getTitle());
        dto.setDescription(announcement.getDescription());
        dto.setCreatedAt(announcement.getCreatedAt());

        if (announcement.getUser() != null) {
            dto.setUserId(announcement.getUser().getId());
        }

        return dto;
    }

    public Announcement toEntity(AnnouncementDTO dto) {
        Announcement announcement = new Announcement();
        announcement.setId(dto.getId());
        announcement.setTitle(dto.getTitle());
        announcement.setDescription(dto.getDescription());
        announcement.setCreatedAt(dto.getCreatedAt());

        return announcement;
    }
}