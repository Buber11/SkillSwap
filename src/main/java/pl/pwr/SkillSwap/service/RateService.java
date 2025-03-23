package pl.pwr.SkillSwap.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.SkillSwap.dto.RatePostRequest;
import pl.pwr.SkillSwap.exception.ResourceNotFoundException;
import pl.pwr.SkillSwap.model.Rate;
import pl.pwr.SkillSwap.model.User;
import pl.pwr.SkillSwap.repository.RateRepository;
import pl.pwr.SkillSwap.repository.UserRepository;

@Service
public class RateService {

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private UserRepository userRepository;

    public Rate addRate(RatePostRequest request) {
        if (request.getSenderId().equals(request.getOwnerId())) {
            throw new IllegalArgumentException("User cannot rate themselves");
        }

        if (request.getValue() < 1 || request.getValue() > 10) {
            throw new IllegalArgumentException("Rate must be between 1 and 10");
        }

        User sender = userRepository.findById(request.getSenderId())
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"));

        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        Rate rate = new Rate();
        rate.setSender(sender);
        rate.setOwner(owner);
        rate.setValue(request.getValue());

        return rateRepository.save(rate);
    }
}
