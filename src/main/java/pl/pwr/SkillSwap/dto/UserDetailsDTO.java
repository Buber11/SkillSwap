package pl.pwr.SkillSwap.dto;

import lombok.Data;

@Data
public class UserDetailsDTO {
    private Long userId;
    private String name;
    private String surname;
    private String description;
    private String username;
}
