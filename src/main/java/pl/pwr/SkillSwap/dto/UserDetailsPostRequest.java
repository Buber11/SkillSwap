package pl.pwr.SkillSwap.dto;

import lombok.Data;

@Data
public class UserDetailsPostRequest {
    private Long userId;
    private String name;
    private String surname;
    private String description;
}
