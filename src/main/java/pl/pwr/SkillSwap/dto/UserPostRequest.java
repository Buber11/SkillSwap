package pl.pwr.SkillSwap.dto;

import lombok.Data;

@Data
public class UserPostRequest {
    private String username;
    private String password;
    private String role;
    private String status;
    private String email;
}

