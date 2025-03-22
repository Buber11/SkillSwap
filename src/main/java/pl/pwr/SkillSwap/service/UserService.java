package pl.pwr.SkillSwap.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.SkillSwap.dto.UserPostRequest;
import pl.pwr.SkillSwap.enums.UserRole;
import pl.pwr.SkillSwap.enums.UserStatus;
import pl.pwr.SkillSwap.model.User;
import pl.pwr.SkillSwap.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserPostRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(UserRole.valueOf(request.getRole()));
        user.setStatus(UserStatus.valueOf(request.getStatus()));
        user.setEmail(request.getEmail());

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

}

