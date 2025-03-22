package pl.pwr.SkillSwap.mapper;

import pl.pwr.SkillSwap.dto.UserDetailsDTO;
import pl.pwr.SkillSwap.model.UserDetails;

public class UserDetailsMapper {

    public static UserDetailsDTO toDTO(UserDetails details) {
        if (details == null) {
            return null;
        }
        UserDetailsDTO dto = new UserDetailsDTO();
        dto.setUserId(details.getUserId());
        dto.setName(details.getName());
        dto.setSurname(details.getSurname());
        dto.setDescription(details.getDescription());
        dto.setUsername(details.getUser().getUsername());
        return dto;
    }
}

