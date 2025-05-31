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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/rate")
@RequiredArgsConstructor
public class RateController {

    private final RateService rateService;


    @PostMapping
    public ResponseEntity<RateDto> addOrUpdateRate(@RequestBody @Valid RatePostRequest request) {
        RateDto rateDto = rateService.addOrUpdateRate(request);
        return ResponseEntity.ok(rateDto);
    }

    @GetMapping("/average/{ownerId}")
    public ResponseEntity<Map<String, Object>> getAverageRate(@PathVariable Long ownerId) {
        Double average = rateService.getAverageRate(ownerId);
        Long count = rateService.countRatesByOwner(ownerId);
        Map<String, Object> response = new HashMap<>();
        response.put("average", average);
        response.put("count", count);
        return ResponseEntity.ok(response);
    }


//    @GetMapping("/{rateId}")
//    public ResponseEntity<RateDto> getRate(@PathVariable Long rateId) {
//        RateDto dto = rateService.getRateById(rateId);
//        return ResponseEntity.ok(dto);
//    }

//    @DeleteMapping("/{rateId}")
//    public ResponseEntity<Void> deleteRate(@PathVariable Long rateId) {
//        rateService.deleteRate(rateId);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/owner/{ownerId}")
//    public ResponseEntity<List<RateDto>> getRatesByOwner(@PathVariable Long ownerId) {
//        List<RateDto> rates = rateService.getRatesByOwner(ownerId);
//        return ResponseEntity.ok(rates);
//    }

    @GetMapping("/{senderId}/{ownerId}")
    public ResponseEntity<RateDto> getUserRateForOwner(
            @PathVariable Long senderId,
            @PathVariable Long ownerId) {
        RateDto rateDto = rateService.getRateBySenderAndOwner(senderId, ownerId);
        if (rateDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rateDto);
    }

}

