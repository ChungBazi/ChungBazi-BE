package chungbazi.chungbazi_be.domain.character.converter;

import chungbazi.chungbazi_be.domain.character.dto.CharacterResponseDTO;
import chungbazi.chungbazi_be.domain.character.entity.Character;
import chungbazi.chungbazi_be.domain.user.dto.UserResponseDTO;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.entity.enums.RewardLevel;
import java.util.List;

public class CharacterConverter {
    public static CharacterResponseDTO.CharacterListDto toCharacterListDto(User user, List<Character> characterList) {
        List<CharacterResponseDTO.CharacterStatusDto> characterStatusList = toCharacterStatusListDto(user, characterList);
        return CharacterResponseDTO.CharacterListDto.builder()
                .userId(user.getId())
                .characterImgList(characterStatusList)
                .build();
    }

    public static List<CharacterResponseDTO.CharacterStatusDto> toCharacterStatusListDto(User user, List<Character> characterList) {
        int maxRewardLevel = user.getReward().getLevel();

        return RewardLevel.getRewardListUpto(maxRewardLevel).stream()
                .map(rewardLevel -> {
                    boolean isOpen = characterList.stream()
                            .filter(character -> character.getRewardLevel() == rewardLevel)
                            .findFirst()
                            .map(Character::isOpen)
                            .orElse(false);
                    return CharacterResponseDTO.CharacterStatusDto.builder()
                            .rewardLevel(rewardLevel.name())
                            .isOpen(isOpen)
                            .build();
                })
                .toList();
    }
}
