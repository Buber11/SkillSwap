package pl.pwr.SkillSwap.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pwr.SkillSwap.dto.UserDetailsDTO;
import pl.pwr.SkillSwap.dto.UserDetailsPostRequest;
import pl.pwr.SkillSwap.dto.UserExtendedDto;
import pl.pwr.SkillSwap.exception.ResourceNotFoundException;
import pl.pwr.SkillSwap.model.User;
import pl.pwr.SkillSwap.model.UserDetails;
import pl.pwr.SkillSwap.service.UserDetailsService;
import org.springframework.security.core.Authentication;
import pl.pwr.SkillSwap.repository.UserRepository;
import pl.pwr.SkillSwap.repository.UserDetailsRepository;



@RestController
@RequestMapping("/api/v1/user-details")
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @PostMapping
    public UserDetailsDTO addOrUpdateUserDetails(@RequestBody @Valid UserDetailsPostRequest request) {
        return userDetailsService.createOrUpdateUserDetails(request);
    }

    @GetMapping("/me")
    public UserExtendedDto getMyDetails(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("Użytkownik nie znaleziony"));
        UserDetails details = userDetailsRepository.findByUserId(user.getId()).orElse(null);
        return mapToUserExtendedDto(user, details);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> getUserById(@PathVariable Long id) {
        UserDetailsDTO dto = userDetailsService.getUserDetailsByUserId(id);
        return ResponseEntity.ok(dto);
    }

    private UserExtendedDto mapToUserExtendedDto(User user, UserDetails details) {
        UserExtendedDto dto = new UserExtendedDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().toString());
        dto.setStatus(user.getStatus().toString());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        if (details != null) {
            dto.setName(details.getName());
            dto.setSurname(details.getSurname());
            dto.setDescription(details.getDescription());
        }
        return dto;
    }
}

