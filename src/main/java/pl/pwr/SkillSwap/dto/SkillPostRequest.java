package pl.pwr.SkillSwap.dto;

import lombok.Data;

@Data
public class SkillPostRequest {
    private String name;
    private String description;
    private String category;
}

