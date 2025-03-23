package pl.pwr.SkillSwap.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.SkillSwap.dto.UserDetailsDTO;
import pl.pwr.SkillSwap.dto.UserDetailsPostRequest;
import pl.pwr.SkillSwap.exception.ResourceNotFoundException;
import pl.pwr.SkillSwap.mapper.UserDetailsMapper;
import pl.pwr.SkillSwap.model.User;
import pl.pwr.SkillSwap.model.UserDetails;
import pl.pwr.SkillSwap.repository.UserDetailsRepository;
import pl.pwr.SkillSwap.repository.UserRepository;

import java.util.Optional;

@Service
public class UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserDetailsDTO createOrUpdateUserDetails(UserDetailsPostRequest dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserDetails details = userDetailsRepository.findById(dto.getUserId())
                .orElse(new UserDetails());

        details.setUser(user);
        details.setName(dto.getName());
        details.setSurname(dto.getSurname());
        details.setDescription(dto.getDescription());

        userDetailsRepository.saveAndFlush(details);

        return UserDetailsMapper.toDTO(details);
    }
}
