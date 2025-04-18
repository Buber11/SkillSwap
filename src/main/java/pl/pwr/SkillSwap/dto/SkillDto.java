package pl.pwr.SkillSwap.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillDto extends RepresentationModel<SkillDto> {
    private Long id;
    private String name;
    private String description;
    private String category;
}
