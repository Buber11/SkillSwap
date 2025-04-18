package pl.pwr.SkillSwap.dto;

import lombok.Data;
import pl.pwr.SkillSwap.enums.SkillLevel;

@Data
public class SkillUserPostRequest {
    private SkillLevel skillLevel;
}
