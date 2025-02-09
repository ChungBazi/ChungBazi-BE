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
    public static UserResponseDTO.ProfileUpdateDto toProfileUpdateDto(User user){
        return UserResponseDTO.ProfileUpdateDto.builder()
                .userId(user.getId())
                .name(user.getName())
                //.profileImg(user.getProfileImg())
                .build();
    }
    public static UserResponseDTO.CharacterListDto toCharacterListDto(User user) {
        List<UserResponseDTO.CharacterStatusDto> characterStatusList = toCharacterStatusListDto(user.getUnlockedLevel(),
                user.getReward().getLevel());
        return UserResponseDTO.CharacterListDto.builder()
                .userId(user.getId())
                .characterImgList(characterStatusList)
                .build();
    }

    public static List<UserResponseDTO.CharacterStatusDto> toCharacterStatusListDto(int unlockedLevel, int maxRewardLevel) {
        return RewardLevel.getRewardListUpto(maxRewardLevel).stream()
                .map(rewardLevel -> UserResponseDTO.CharacterStatusDto.builder()
                        .rewardLevel(rewardLevel.name())
                        .isOpen(rewardLevel.getLevel() <= unlockedLevel)
                        .build())
                .toList();
    }
}
