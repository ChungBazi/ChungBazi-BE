package chungbazi.chungbazi_be.domain.character.converter;

import chungbazi.chungbazi_be.domain.character.dto.CharacterResponseDTO;
import chungbazi.chungbazi_be.domain.character.entity.Character;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.entity.enums.RewardLevel;
import java.util.List;

public class CharacterConverter {

    public static List<CharacterResponseDTO.CharacterListDto> toCharacterListDto(User user, List<Character> characterList) {
        int maxRewardLevel = user.getReward().getLevel();

        return RewardLevel.getRewardListUpto(maxRewardLevel).stream()
                .map(rewardLevel -> {
                    boolean isOpen = characterList.stream()
                            .filter(character -> character.getRewardLevel() == rewardLevel)
                            .findFirst()
                            .map(Character::isOpen)
                            .orElse(false);
                    return CharacterResponseDTO.CharacterListDto.builder()
                            .rewardLevel(rewardLevel.name())
                            .isOpen(isOpen)
                            .build();
                })
                .toList();
    }

    public static CharacterResponseDTO.MainCharacterDto toMainCharacterDto(Character character) {
        return CharacterResponseDTO.MainCharacterDto.builder()
                .rewardLevel(character.getRewardLevel().name())
                .build();
    }
}
