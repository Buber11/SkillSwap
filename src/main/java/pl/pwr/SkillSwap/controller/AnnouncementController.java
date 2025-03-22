package pl.pwr.SkillSwap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.pwr.SkillSwap.dto.AnnouncementGetRequest;
import pl.pwr.SkillSwap.dto.AnnouncementPostRequest;
import pl.pwr.SkillSwap.enums.VisibilityType;
import pl.pwr.SkillSwap.model.Announcement;
import pl.pwr.SkillSwap.service.AnnouncementService;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping
    public Page<AnnouncementGetRequest> getAllAnnouncements(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) VisibilityType visibility,
            Pageable pageable
    ) {
        return announcementService.getAllAnnouncements(pageable);
    }

    @PostMapping
    public Announcement createAnnouncement(@RequestBody AnnouncementPostRequest request) {
        return announcementService.createAnnouncement(request);
    }
}
