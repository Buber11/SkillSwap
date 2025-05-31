package pl.pwr.SkillSwap.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pwr.SkillSwap.dto.SkillDto;
import pl.pwr.SkillSwap.dto.SkillPostRequest;
import pl.pwr.SkillSwap.service.SkillService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skill")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;


    @PostMapping()
    public ResponseEntity<SkillDto> createSkill(
            @RequestBody @Valid SkillPostRequest request) {
        return ResponseEntity.ok(skillService.createSkill(request));
    }

    @GetMapping("/{skillId}")
    public ResponseEntity<SkillDto> getSkill(
            @PathVariable("skillId") Long skillId) {
        return ResponseEntity.ok(skillService.getSkill(skillId));
    }

    @GetMapping()
    public ResponseEntity<List<SkillDto>> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PagedModel<EntityModel<SkillDto>>> getSkillsByUserId(
            @PathVariable("userId") Long userId,
            @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(skillService.getSkillsByUserId(userId, pageable));
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<Void> deleteSkill(
            @PathVariable("skillId") Long skillId) {
        skillService.deleteSkill(skillId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{skillId}")
    public ResponseEntity<SkillDto> updateSkill(
            @PathVariable("skillId") Long skillId,
            @RequestBody @Valid SkillPostRequest request) {
        return ResponseEntity.ok(skillService.updateSkill(skillId, request));
    }

}
