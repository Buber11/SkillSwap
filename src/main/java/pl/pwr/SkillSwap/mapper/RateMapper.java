package pl.pwr.SkillSwap.mapper;

import org.springframework.stereotype.Component;
import pl.pwr.SkillSwap.controller.RateController;
import pl.pwr.SkillSwap.dto.RateDto;
import pl.pwr.SkillSwap.dto.RatePostRequest;
import pl.pwr.SkillSwap.model.Rate;
import pl.pwr.SkillSwap.model.User;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RateMapper{

    public RateDto toDto(Rate rate) {
        if (rate == null) {
            return null;
        }
        RateDto dto = new RateDto();
        dto.setId(rate.getId());
        dto.setSenderId(rate.getSender().getId());
        dto.setOwnerId(rate.getOwner().getId());
        dto.setValue(rate.getValue());

        dto.add(linkTo(methodOn(RateController.class).getRate(rate.getId()))
                .withSelfRel());
        System.out.println("getRate: " + rate.getId());
        dto.add(linkTo(methodOn(RateController.class)
                .deleteRate(rate.getId()))
                .withRel("delete"));
        System.out.println("deleteRate: " + rate.getId());
        dto.add(linkTo(methodOn(RateController.class)
                .updateRate(rate.getId(), null))
                .withRel("update"));
        System.out.println("updateRate: " + rate.getId());
        dto.add(linkTo(methodOn(RateController.class)
                .addRate(null))
                .withRel("add"));
        System.out.println("addRate: " + rate.getId());
        dto.add(linkTo(methodOn(RateController.class)
                .getRate(rate.getOwner().getId()))
                .withRel("getAllRates"));
        System.out.println("getAllRates: " + rate.getOwner().getId());
        return dto;
    }

    public Rate toEntity(RatePostRequest request, User owner, User sender) {
        if (request == null) {
            return null;
        }
        Rate rate = new Rate();
        rate.setSender(sender);
        rate.setOwner(owner);
        rate.setValue(request.getValue());
        return rate;
    }
}
