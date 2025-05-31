package pl.pwr.SkillSwap.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.SkillSwap.dto.SkillUserPostRequest;
import pl.pwr.SkillSwap.dto.UserExtendedDto;
import pl.pwr.SkillSwap.dto.UserPostRequest;
import pl.pwr.SkillSwap.dto.UserRatingDTO;
import pl.pwr.SkillSwap.enums.UserRole;
import pl.pwr.SkillSwap.enums.UserStatus;
import pl.pwr.SkillSwap.exception.ResourceNotFoundException;
import pl.pwr.SkillSwap.model.Skill;
import pl.pwr.SkillSwap.model.SkillUser;
import pl.pwr.SkillSwap.model.User;
import pl.pwr.SkillSwap.model.UserDetails;
import pl.pwr.SkillSwap.repository.UserDetailsRepository;
import pl.pwr.SkillSwap.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository; 
    private final BCryptPasswordEncoder passwordEncoder;
    private final SkillService skillService;

    public User createUser(UserPostRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.valueOf(request.getRole()));
        user.setStatus(UserStatus.valueOf(request.getStatus()));
        user.setEmail(request.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public Page<UserRatingDTO> getUsersWithAverageRating(Pageable pageable) {
        return userRepository.findAllWithAverageRating(pageable);
    }
    
    @Transactional
    public void createSkillUser(Long userId, Long skillId, SkillUserPostRequest request) {
        User user = userRepository.findUserWithSkillsById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Skill skill = skillService.getSkillEntity(skillId);
        SkillUser skillUser = new SkillUser(
                request.getSkillLevel(),
                skill,
                user
        );
        user.addSkill(skillUser);
        userRepository.save(user);
    }   

    @Transactional
    public User updateUserStatus(Long userId, UserStatus newStatus) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        user.setStatus(newStatus);
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userDetailsRepository.deleteById(userId);
        userRepository.deleteById(userId);
    }
    
    private UserExtendedDto mapToUserExtendedDto(User user, UserDetails userDetails) {
        UserExtendedDto dto = new UserExtendedDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().toString());
        dto.setStatus(user.getStatus().toString());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        if (userDetails != null) {
            dto.setName(userDetails.getName());
            dto.setSurname(userDetails.getSurname());
            dto.setDescription(userDetails.getDescription());
        }
        return dto;
    }

    public List<UserExtendedDto> getAllUserExtendedDtos() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> {
                    UserDetails details = userDetailsRepository.findByUserId(user.getId()).orElse(null);
                    return mapToUserExtendedDto(user, details);
                })
                .collect(Collectors.toList());
    }
    

}
