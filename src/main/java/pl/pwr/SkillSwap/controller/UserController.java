package pl.pwr.SkillSwap.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.pwr.SkillSwap.dto.UserPostRequest;
import pl.pwr.SkillSwap.dto.UserRatingDTO;
import pl.pwr.SkillSwap.model.User;
import pl.pwr.SkillSwap.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody @Valid UserPostRequest request) {
        System.out.println("Creating user: " + request);
        return userService.createUser(request);
    }

    @GetMapping("/ratings")
    public Page<UserRatingDTO> getUsersWithRatings(Pageable pageable) {
        return userService.getUsersWithAverageRating(pageable);
    }

}

