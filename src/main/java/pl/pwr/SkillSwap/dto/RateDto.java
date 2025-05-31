package pl.pwr.SkillSwap.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RateDto extends RepresentationModel<RateDto> {
    private Long id;
    private Long senderId;
    private Long ownerId;
    private Double value;

}
