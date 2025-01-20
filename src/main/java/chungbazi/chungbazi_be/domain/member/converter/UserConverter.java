package chungbazi.chungbazi_be.domain.member.converter;

import chungbazi.chungbazi_be.domain.member.dto.UserResponseDTO;
import chungbazi.chungbazi_be.domain.member.entity.User;

public class UserConverter {
    public static UserResponseDTO.ProfileDto toProfileDto(User user) {
        return UserResponseDTO.ProfileDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
