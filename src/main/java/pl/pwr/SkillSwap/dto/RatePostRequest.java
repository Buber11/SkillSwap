package pl.pwr.SkillSwap.dto;

import lombok.Data;

@Data
public class RatePostRequest {
    private Long senderId;
    private Long ownerId;
    private Double value;
}
