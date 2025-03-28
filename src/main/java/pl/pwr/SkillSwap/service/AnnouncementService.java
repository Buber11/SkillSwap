package pl.pwr.SkillSwap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.pwr.SkillSwap.dto.AnnouncementDTO;
import pl.pwr.SkillSwap.dto.AnnouncementPostRequest;
import pl.pwr.SkillSwap.enums.VisibilityType;
import pl.pwr.SkillSwap.exception.ResourceNotFoundException;
import pl.pwr.SkillSwap.model.Announcement;
import pl.pwr.SkillSwap.model.Skill;
import pl.pwr.SkillSwap.model.User;
import pl.pwr.SkillSwap.repository.AnnouncementRepository;
import pl.pwr.SkillSwap.repository.SkillRepository;
import pl.pwr.SkillSwap.repository.UserRepository;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillRepository skillRepository;

    public Page<AnnouncementDTO> getAllAnnouncements(Pageable pageable) {
        return announcementRepository.findAllWithUserDetails(pageable);
    }

    public Announcement createAnnouncement(AnnouncementPostRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + request.getUserId()));

        Skill skill = null;
        if (request.getSkillId() != null) {
            skill = skillRepository.findById(request.getSkillId())
                    .orElseThrow(() -> new ResourceNotFoundException("Skill not found with ID: " + request.getSkillId()));
        }

        Announcement announcement = new Announcement();
        announcement.setUser(user);
        announcement.setSkill(skill);
        announcement.setTitle(request.getTitle());
        announcement.setDescription(request.getDescription());
        announcement.setVisibility(VisibilityType.valueOf(request.getVisibility()));

        return announcementRepository.save(announcement);
    }
}
