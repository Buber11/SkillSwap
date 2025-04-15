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

@Service
@RequiredArgsConstructor
public class RateService {

    private final RateRepository rateRepository;
    private final UserRepository userRepository;
    private final RateMapper rateMapper;

    public RateDto addRate(RatePostRequest request) {
        if (request.getSenderId().equals(request.getOwnerId())) {
            throw new IllegalArgumentException("User cannot rate themselves");
        }
        System.out.println("Sender ID: " + request.getSenderId());
        System.out.println("Owner ID: " + request.getOwnerId());
        System.out.println("Rate value: " + request.getValue());

        User sender = userRepository.findById(request.getSenderId())
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"));

        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
        System.out.println("Sender: " + sender.getId());
        System.out.println("Owner: " + owner.getId());

        Rate rate = new Rate();
        rate.setSender(sender);
        rate.setOwner(owner);
        rate.setValue(request.getValue());
        System.out.println("Rate value: " + rate.getValue());

        return rateMapper.toDto(rateRepository.save(rate));
    }

   public Page<RateDto> getRates(Long userId, Pageable pageable) {
        Page<Rate> rates = rateRepository.findByOwnerId(userId, pageable);
        if (rates.isEmpty()) {
            throw new ResourceNotFoundException("No rates found for user with id: " + userId);
        }
        return rates.map( rate -> rateMapper.toDto(rate));
    }

    public RateDto getRate(Long rateId) {
        Rate rate = rateRepository.findById(rateId)
                .orElseThrow(() -> new ResourceNotFoundException("Rate not found"));
        return rateMapper.toDto(rate);
    }

    public void deleteRate(Long rateId) {
        Rate rate = rateRepository.findById(rateId)
                .orElseThrow(() -> new ResourceNotFoundException("Rate not found"));
        rateRepository.delete(rate);
    }

    public RateDto updateRate(Long rateId, RatePostRequest request) {
        Rate rate = rateRepository.findById(rateId)
                .orElseThrow(() -> new ResourceNotFoundException("Rate not found"));

        rate.setValue(request.getValue());
        return rateMapper.toDto(rateRepository.save(rate));
    }


}
