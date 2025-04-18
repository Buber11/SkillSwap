package pl.pwr.SkillSwap.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pwr.SkillSwap.dto.SkillDto;
import pl.pwr.SkillSwap.dto.SkillUserPostRequest;
import pl.pwr.SkillSwap.dto.UserPostRequest;
import pl.pwr.SkillSwap.dto.UserRatingDTO;
import pl.pwr.SkillSwap.model.User;
import pl.pwr.SkillSwap.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/rating")
    public Page<UserRatingDTO> getUsersWithRatings(Pageable pageable) {
        return userService.getUsersWithAverageRating(pageable);
    }

    @PostMapping("{userId}/skill/{skillId}")
    public ResponseEntity<Void> addSkillToUser(
            @PathVariable("userId") String userId,
            @PathVariable("skillId") Long skillId,
            @RequestBody @Valid SkillUserPostRequest request) {
        Long userIdLong = Long.valueOf(userId);
        userService.createSkillUser(userIdLong, skillId, request);
        return ResponseEntity.noContent().build();
    }







}

