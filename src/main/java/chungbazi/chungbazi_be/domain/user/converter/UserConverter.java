package chungbazi.chungbazi_be.domain.user.converter;

import chungbazi.chungbazi_be.domain.user.dto.UserResponseDTO;
import chungbazi.chungbazi_be.domain.user.entity.User;

public class UserConverter {
    public static UserResponseDTO.ProfileDto toProfileDto(User user) {
        return UserResponseDTO.ProfileDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
