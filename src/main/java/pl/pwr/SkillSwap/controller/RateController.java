package pl.pwr.SkillSwap.controller;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pwr.SkillSwap.dto.RateDto;
import pl.pwr.SkillSwap.dto.RatePostRequest;
import pl.pwr.SkillSwap.service.RateService;

@RestController
@RequestMapping("/api/v1/rate")
@RequiredArgsConstructor
public class RateController {

    private final RateService rateService;

    @PostMapping()
    public ResponseEntity<RateDto> addRate(
            @RequestBody @Valid RatePostRequest request) {
        return ResponseEntity.ok(rateService.addRate(request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<RateDto>> getRates(
            @PathVariable("userId") Long userId,
            @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable) {
        Page<RateDto> rates = rateService.getRates(userId, pageable);
        return ResponseEntity.ok(rates);
    }

    @GetMapping("/{rateId}")
    public ResponseEntity<RateDto> getRate(
            @PathVariable("rateId") Long rateId) {
        RateDto rate = rateService.getRate(rateId);
        return ResponseEntity.ok(rate);
    }

    @DeleteMapping("/{rateID}")
    public ResponseEntity<Void> deleteRate(
            @PathVariable("rateId") Long rateId) {
        rateService.deleteRate(rateId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{rateId}")
    public ResponseEntity<RateDto> updateRate(
            @PathParam("rateId") Long rateId,
            @RequestBody @Valid RatePostRequest request) {
        RateDto updatedRate = rateService.updateRate(rateId, request);
        return ResponseEntity.ok(updatedRate);
    }


}

