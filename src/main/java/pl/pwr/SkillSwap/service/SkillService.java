package pl.pwr.SkillSwap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.SkillSwap.dto.SkillPostRequest;
import pl.pwr.SkillSwap.model.Skill;
import pl.pwr.SkillSwap.repository.SkillRepository;

import java.time.LocalDateTime;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public Skill createSkill(SkillPostRequest request) {
        Skill skill = new Skill();
        skill.setName(request.getName());
        skill.setDescription(request.getDescription());
        skill.setCategory(request.getCategory());

        return skillRepository.save(skill);
    }
}

