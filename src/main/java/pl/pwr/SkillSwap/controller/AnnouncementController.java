package pl.pwr.SkillSwap.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.pwr.SkillSwap.dto.AnnouncementDTO;
import pl.pwr.SkillSwap.dto.AnnouncementPostRequest;
import pl.pwr.SkillSwap.model.Announcement;
import pl.pwr.SkillSwap.service.AnnouncementService;

@RestController
@RequestMapping("/api/v1/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    // For example: GET /announcements?sort=title,asc
    // or: http://localhost:8080/api/announcements?sort=user.username,desc
    @GetMapping
    public Page<AnnouncementDTO> getAnnouncements(Pageable pageable) {
        return announcementService.getAllAnnouncements(pageable);
    }

    @PostMapping
    public Announcement createAnnouncement(@RequestBody @Valid AnnouncementPostRequest request) {
        return announcementService.createAnnouncement(request);
    }
}
