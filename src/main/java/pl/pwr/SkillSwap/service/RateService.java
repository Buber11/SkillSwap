package pl.pwr.SkillSwap.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pwr.SkillSwap.dto.RateDto;
import pl.pwr.SkillSwap.dto.RatePostRequest;
import pl.pwr.SkillSwap.exception.ResourceNotFoundException;
import pl.pwr.SkillSwap.mapper.RateMapper;
import pl.pwr.SkillSwap.model.Rate;
import pl.pwr.SkillSwap.model.User;
import pl.pwr.SkillSwap.repository.RateRepository;
import pl.pwr.SkillSwap.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RateService {

    private final RateRepository rateRepository;
    private final UserRepository userRepository;
    private final RateMapper rateMapper;

    public RateDto addOrUpdateRate(RatePostRequest request) {
        if (request.getSenderId().equals(request.getOwnerId())) {
            throw new IllegalArgumentException("User cannot rate themselves");
        }

        User sender = userRepository.findById(request.getSenderId())
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"));
        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        Optional<Rate> existingRateOpt = rateRepository.findBySenderIdAndOwnerId(sender.getId(), owner.getId());

        Rate rate;
        if (existingRateOpt.isPresent()) {
            // Aktualizacja istniejącej oceny
            rate = existingRateOpt.get();
            rate.setValue(request.getValue());
        } else {
            // Tworzenie nowej oceny
            rate = new Rate();
            rate.setSender(sender);
            rate.setOwner(owner);
            rate.setValue(request.getValue());
        }

        rateRepository.save(rate);
        return rateMapper.toDto(rate);
    }

    public Double getAverageRate(Long ownerId) {
        Double avg = rateRepository.findAverageByOwnerId(ownerId);
        return avg != null ? avg : 0.0;
    }

    public RateDto getRateById(Long rateId) {
        Rate rate = rateRepository.findById(rateId)
                .orElseThrow(() -> new ResourceNotFoundException("Rate not found"));
        return rateMapper.toDto(rate);
    }

    public void deleteRate(Long rateId) {
        Rate rate = rateRepository.findById(rateId)
                .orElseThrow(() -> new ResourceNotFoundException("Rate not found"));
        rateRepository.delete(rate);
    }

    public List<RateDto> getRatesByOwner(Long ownerId) {
        List<Rate> rates = rateRepository.findByOwnerId(ownerId);
        if (rates.isEmpty()) {
            throw new ResourceNotFoundException("No rates found for user with id: " + ownerId);
        }
        return rates.stream()
                .map(rateMapper::toDto)
                .toList();
    }

    public Long countRatesByOwner(Long ownerId) {
        return rateRepository.countByOwnerId(ownerId);
    }

    public RateDto getRateBySenderAndOwner(Long senderId, Long ownerId) {
        return rateRepository.findBySenderIdAndOwnerId(senderId, ownerId)
                .map(rateMapper::toDto)
                .orElse(null);
    }


}

