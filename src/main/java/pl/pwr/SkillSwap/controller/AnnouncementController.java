package pl.pwr.SkillSwap.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
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
//    @GetMapping
//    public Page<AnnouncementDTO> getAnnouncements(Pageable pageable) {
//        return announcementService.getAllAnnouncements(pageable);
//    }

    @PostMapping
    public AnnouncementDTO createAnnouncement(@RequestBody @Valid AnnouncementPostRequest request) {
        return announcementService.createAnnouncementDTO(request);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<AnnouncementDTO>>> getAnnouncements(
            @PageableDefault Pageable pageable,
            PagedResourcesAssembler<AnnouncementDTO> assembler) {

        Page<AnnouncementDTO> page = announcementService.getAllAnnouncements(pageable);
        PagedModel<EntityModel<AnnouncementDTO>> pagedModel = assembler.toModel(page);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PagedModel<EntityModel<AnnouncementDTO>>> getAnnouncementsByUserId(
            @PathVariable Long userId,
            @PageableDefault Pageable pageable,
            PagedResourcesAssembler<AnnouncementDTO> assembler) {

        Page<AnnouncementDTO> page = announcementService.getAnnouncementsByUserId(userId, pageable);
        PagedModel<EntityModel<AnnouncementDTO>> pagedModel = assembler.toModel(page);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementDTO> getAnnouncementById(@PathVariable Long id) {
        AnnouncementDTO announcement = announcementService.getAnnouncementById(id);
        if (announcement == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(announcement);
    }


    @PutMapping("/{id}")
    public ResponseEntity<AnnouncementDTO> updateAnnouncement(
            @PathVariable Long id,
            @RequestBody @Valid AnnouncementPostRequest request) {

        AnnouncementDTO updated = announcementService.updateAnnouncement(id, request);
        return ResponseEntity.ok(updated);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        boolean deleted = announcementService.deleteAnnouncement(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
