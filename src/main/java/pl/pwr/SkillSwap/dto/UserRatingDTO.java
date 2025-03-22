package pl.pwr.SkillSwap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRatingDTO {
    private Long userId;
    private String username;
    private String email;
    private String name;
    private String surname;
    private String description;
    private BigDecimal averageRate;
}

