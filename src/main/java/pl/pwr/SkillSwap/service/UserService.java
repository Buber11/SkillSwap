package pl.pwr.SkillSwap.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.SkillSwap.dto.SkillUserPostRequest;
import pl.pwr.SkillSwap.dto.UserPostRequest;
import pl.pwr.SkillSwap.dto.UserRatingDTO;
import pl.pwr.SkillSwap.enums.UserRole;
import pl.pwr.SkillSwap.enums.UserStatus;
import pl.pwr.SkillSwap.model.Skill;
import pl.pwr.SkillSwap.model.SkillUser;
import pl.pwr.SkillSwap.model.SkillUserId;
import pl.pwr.SkillSwap.model.User;
import pl.pwr.SkillSwap.repository.UserRepository;

import java.security.PublicKey;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
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

}

