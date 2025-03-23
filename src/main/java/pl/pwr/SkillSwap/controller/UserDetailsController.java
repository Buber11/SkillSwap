package pl.pwr.SkillSwap.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pwr.SkillSwap.dto.UserDetailsDTO;
import pl.pwr.SkillSwap.dto.UserDetailsPostRequest;
import pl.pwr.SkillSwap.service.UserDetailsService;

@RestController
@RequestMapping("/api/user-details")
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping
    public UserDetailsDTO addOrUpdateUserDetails(@RequestBody @Valid UserDetailsPostRequest request) {
        return userDetailsService.createOrUpdateUserDetails(request);
    }
}

