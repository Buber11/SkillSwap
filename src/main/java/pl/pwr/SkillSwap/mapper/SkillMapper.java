package pl.pwr.SkillSwap.mapper;


import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import pl.pwr.SkillSwap.controller.SkillController;
import pl.pwr.SkillSwap.controller.UserController;
import pl.pwr.SkillSwap.dto.SkillDto;
import pl.pwr.SkillSwap.dto.SkillPostRequest;
import pl.pwr.SkillSwap.model.Skill;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SkillMapper extends RepresentationModelAssemblerSupport<Skill, SkillDto> {

    public SkillMapper() {
        super(SkillController.class, SkillDto.class);
    }

    public Skill toSkill(SkillPostRequest skillPostRequest) {
        return Skill.builder()
                .name(skillPostRequest.getName())
                .description(skillPostRequest.getDescription())
                .category(skillPostRequest.getCategory())
                .build();
    }

    public SkillDto toSkillDto(Skill skill){
        SkillDto skillDto = SkillDto.builder()
                .id(skill.getId())
                .name(skill.getName())
                .description(skill.getDescription())
                .category(skill.getCategory())
                .build();

        skillDto.add(linkTo(methodOn(SkillController.class)
                        .getSkill(skill.getId())).withSelfRel());
        skillDto.add(linkTo(methodOn(SkillController.class)
                .createSkill(null)).withRel("create"));
        skillDto.add(linkTo(methodOn(SkillController.class)
                .updateSkill(skill.getId(),null)).withRel("update"));
        skillDto.add(linkTo(methodOn(SkillController.class)
                .deleteSkill(skill.getId())).withRel("delete"));
        skillDto.add(linkTo(methodOn(UserController.class)
                .addSkillToUser("userId", skill.getId(), null)).withRel("addToUser"));

        return skillDto;
    }

    @Override
    public SkillDto toModel(Skill entity) {
        return toSkillDto(entity);
    }
}
