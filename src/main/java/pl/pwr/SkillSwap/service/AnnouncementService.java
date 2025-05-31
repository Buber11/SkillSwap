package pl.pwr.SkillSwap.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.pwr.SkillSwap.dto.AnnouncementDTO;
import pl.pwr.SkillSwap.dto.AnnouncementPostRequest;
import pl.pwr.SkillSwap.enums.VisibilityType;
import pl.pwr.SkillSwap.exception.ResourceNotFoundException;
import pl.pwr.SkillSwap.mapper.AnnouncementMapper;
import pl.pwr.SkillSwap.model.Announcement;
import pl.pwr.SkillSwap.model.Skill;
import pl.pwr.SkillSwap.model.User;
import pl.pwr.SkillSwap.repository.AnnouncementRepository;
import pl.pwr.SkillSwap.repository.SkillRepository;
import pl.pwr.SkillSwap.repository.UserRepository;

import java.util.Optional;

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

    // Nowa metoda, która tworzy i zwraca DTO
    public AnnouncementDTO createAnnouncementDTO(AnnouncementPostRequest request) {
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

        Announcement saved = announcementRepository.save(announcement);

        return new AnnouncementDTO(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getVisibility(),
                saved.getCreatedAt(),
                saved.getUpdatedAt(),
                saved.getSkill() != null ? saved.getSkill().getId() : null,
                saved.getUser().getId(),
                saved.getUser().getUserDetails().getName(),
                saved.getUser().getUserDetails().getSurname()
        );
    }

    public Page<AnnouncementDTO> getAnnouncementsByUserId(Long userId, Pageable pageable) {
        Page<Announcement> announcements = announcementRepository.findByUserId(userId, pageable);

        return announcements.map(a -> new AnnouncementDTO(
                a.getId(),
                a.getTitle(),
                a.getDescription(),
                a.getVisibility(),
                a.getCreatedAt(),
                a.getUpdatedAt(),
                a.getSkill() != null ? a.getSkill().getId() : null,
                a.getUser().getId(),
                a.getUser().getUserDetails().getName(),
                a.getUser().getUserDetails().getSurname()
        ));
    }


    private final AnnouncementMapper announcementMapper;

    @Autowired
    public AnnouncementService(
            AnnouncementRepository announcementRepository,
            AnnouncementMapper announcementMapper
    ) {
        this.announcementRepository = announcementRepository;
        this.announcementMapper = announcementMapper;
    }

    public AnnouncementDTO getAnnouncementById(Long id) {
        return announcementRepository.findById(id)
                .map(announcementMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Ogłoszenie nie istnieje"));

    }


    public boolean deleteAnnouncement(Long id) {
        Optional<Announcement> announcement = announcementRepository.findById(id);
        if (announcement.isPresent()) {
            announcementRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public AnnouncementDTO updateAnnouncement(Long id, AnnouncementPostRequest request) {
        Announcement existing = announcementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ogłoszenie o ID " + id + " nie istnieje"));

        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setVisibility(VisibilityType.valueOf(request.getVisibility().toUpperCase()));


        if (request.getSkillId() != null) {
            Skill skill = skillRepository.findById(request.getSkillId())
                    .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono umiejętności o ID " + request.getSkillId()));
            existing.setSkill(skill);
        }

        Announcement updated = announcementRepository.save(existing);
        return announcementMapper.toDTO(updated);
    }


}