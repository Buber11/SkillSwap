package pl.pwr.SkillSwap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pwr.SkillSwap.dto.RatePostRequest;
import pl.pwr.SkillSwap.model.Rate;
import pl.pwr.SkillSwap.service.RateService;

@RestController
@RequestMapping("/api/rates")
public class RateController {

    @Autowired
    private RateService rateService;

    @PostMapping
    public Rate addRate(@RequestBody RatePostRequest request) {
        return rateService.addRate(request);
    }
}

