package pl.pwr.SkillSwap.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserExtendedDto {
    // Dane z encji User
    private Long id;
    private String username;
    private String email;
    private String role;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Dane z encji UserDetails
    private String name;
    private String surname;
    private String description;
}
