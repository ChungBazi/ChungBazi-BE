package chungbazi.chungbazi_be.domain.character.service;

import chungbazi.chungbazi_be.domain.auth.jwt.SecurityUtils;
import chungbazi.chungbazi_be.domain.character.converter.CharacterConverter;
import chungbazi.chungbazi_be.domain.character.dto.CharacterResponseDTO;
import chungbazi.chungbazi_be.domain.character.entity.Character;
import chungbazi.chungbazi_be.domain.character.repository.CharacterRepository;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.entity.enums.RewardLevel;
import chungbazi.chungbazi_be.domain.user.repository.UserRepository;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CharacterService {
    private final UserRepository userRepository;
    private final CharacterRepository characterRepository;

    public CharacterResponseDTO.CharacterListDto getCharacters() {
        Long userId = SecurityUtils.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));

        List<Character> characterList = characterRepository.findByUserId(userId);

        return CharacterConverter.toCharacterListDto(user, characterList);
    }

    public CharacterResponseDTO.SelectedCharacterDto selectOrOpen(String selectedLevel) {
        Long userId = SecurityUtils.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));

        RewardLevel targetLevel = RewardLevel.valueOf(selectedLevel);

        // 유저 레벨보다 높은 캐릭터 선택 시 에러 핸들링
        if (targetLevel.getLevel() > user.getReward().getLevel()) {
            throw new BadRequestHandler(ErrorStatus.INVALID_CHARACTER);
        }

        Character character = characterRepository.findByUserIdAndRewardLevel(userId, targetLevel)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_CHARACTER));

        // 캐릭터 잠겨있는 경우 오픈
        if (!character.isOpen()) {
            character.setOpen(true);
            characterRepository.save(character);
        }
        return CharacterConverter.toSelectedCharacterDto(character);
    }
}
