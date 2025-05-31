package pl.pwr.SkillSwap.service;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import pl.pwr.SkillSwap.dto.SkillDto;
import pl.pwr.SkillSwap.dto.SkillPostRequest;
import pl.pwr.SkillSwap.model.Skill;

import java.util.List;

public interface SkillService {

    SkillDto createSkill(SkillPostRequest request);

    SkillDto getSkill(Long skillId);

    Skill getSkillEntity(Long skillId);

    SkillDto updateSkill(Long skillId, SkillPostRequest request);

    void deleteSkill(Long skillId);

    PagedModel<EntityModel<SkillDto>> getSkillsByUserId(Long userId, Pageable pageable);

    List<SkillDto> getAllSkills();
}
