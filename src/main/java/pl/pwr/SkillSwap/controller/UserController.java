package pl.pwr.SkillSwap.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pwr.SkillSwap.dto.SkillUserPostRequest;
import pl.pwr.SkillSwap.dto.UserExtendedDto;
import pl.pwr.SkillSwap.dto.UserRatingDTO;
import pl.pwr.SkillSwap.enums.UserStatus;
import pl.pwr.SkillSwap.model.User;
import pl.pwr.SkillSwap.model.UserDetails;
import pl.pwr.SkillSwap.repository.UserDetailsRepository;
import pl.pwr.SkillSwap.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @Autowired
    private UserDetailsRepository userDetailsRepository;

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

    @GetMapping
    public ResponseEntity<List<UserExtendedDto>> getAllUsers() {
        List<UserExtendedDto> users = userService.getAllUserExtendedDtos();
        return ResponseEntity.ok(users);
    }
    @PutMapping("/{userId}/status")
    public ResponseEntity<UserExtendedDto> updateUserStatus(
            @PathVariable Long userId,
            @RequestBody Map<String, String> payload) {
        String statusStr = payload.get("status");
        if (statusStr == null) {
            return ResponseEntity.badRequest().build();
        }
        UserStatus newStatus = UserStatus.valueOf(statusStr.toUpperCase());
        User updatedUser = userService.updateUserStatus(userId, newStatus);
        
        UserDetails userDetails = userDetailsRepository.findByUserId(userId).orElse(null);
        
        UserExtendedDto dto = new UserExtendedDto();
        dto.setId(updatedUser.getId());
        dto.setUsername(updatedUser.getUsername());
        dto.setEmail(updatedUser.getEmail());
        dto.setRole(updatedUser.getRole().toString());
        dto.setStatus(updatedUser.getStatus().toString());
        dto.setCreatedAt(updatedUser.getCreatedAt());
        dto.setUpdatedAt(updatedUser.getUpdatedAt());
        if (userDetails != null) {
            dto.setName(userDetails.getName());
            dto.setSurname(userDetails.getSurname());
            dto.setDescription(userDetails.getDescription());
        }
        
        return ResponseEntity.ok(dto);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);  
        return ResponseEntity.noContent().build();
    }
}
