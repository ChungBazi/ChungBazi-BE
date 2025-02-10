package chungbazi.chungbazi_be.domain.user.converter;

import chungbazi.chungbazi_be.domain.user.dto.UserResponseDTO;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.entity.enums.RewardLevel;
import java.util.List;

public class UserConverter {
    public static UserResponseDTO.ProfileDto toProfileDto(User user) {
        return UserResponseDTO.ProfileDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .characterImg(user.getCharacterImg())
                .build();
    }

    public static UserResponseDTO.RewardDto toRewardDto(int rewardLevel, int postCount, int commentCount) {
        return UserResponseDTO.RewardDto.builder()
                .rewardLevel(rewardLevel)
                .postCount(postCount)
                .commentCount(commentCount)
                .build();
    }
}
