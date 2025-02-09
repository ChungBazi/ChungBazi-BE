package chungbazi.chungbazi_be.domain.character.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class CharacterResponseDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class CharacterListDto {
        Long userId;
        List<CharacterStatusDto> characterImgList;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    public static class CharacterStatusDto {
        String rewardLevel;
        boolean isOpen;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    public static class SelectedCharacterDto {
        String rewardLevel;
    }
}
