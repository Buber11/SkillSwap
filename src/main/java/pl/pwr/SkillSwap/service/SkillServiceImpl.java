package pl.pwr.SkillSwap.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.SkillSwap.dto.SkillDto;
import pl.pwr.SkillSwap.dto.SkillPostRequest;
import pl.pwr.SkillSwap.exception.ResourceNotFoundException;
import pl.pwr.SkillSwap.mapper.SkillMapper;
import pl.pwr.SkillSwap.model.Skill;
import pl.pwr.SkillSwap.repository.SkillRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;
    private final PagedResourcesAssembler<SkillDto> pagedResourcesAssembler;

    @Transactional
    public SkillDto createSkill(SkillPostRequest request) {
        Skill skill = skillMapper.toSkill(request);
        return skillMapper.toSkillDto(
                skillRepository.save(skill)
        );
    }
    @Transactional(readOnly = true)
    public SkillDto getSkill(Long skillId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));
        return skillMapper.toSkillDto(skill);
    }
    @Transactional(readOnly = true)
    public Skill getSkillEntity(Long skillId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));
        return skill;
    }
    @Transactional
    public SkillDto updateSkill(Long skillId, SkillPostRequest request) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));
        skill.setName(request.getName());
        skill.setDescription(request.getDescription());
        skill.setCategory(request.getCategory());
        skill = skillRepository.save(skill);
        return skillMapper.toSkillDto(skill);
    }
    @Transactional
    public void deleteSkill(Long skillId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));
        skillRepository.delete(skill);
    }
    @Transactional(readOnly = true)
    public PagedModel<EntityModel<SkillDto>> getSkillsByUserId(Long userId, Pageable pageable) {
         return pagedResourcesAssembler.toModel(
                 skillRepository.findByUser(userId, pageable)
                         .map(skillMapper::toSkillDto)
         );
    }
    @Transactional(readOnly = true)
    public List<SkillDto> getAllSkills() {
        return skillRepository.findAll().stream()
                .map(skillMapper::toSkillDto)
                .toList();
    }

}

