package chungbazi.chungbazi_be.domain.character.service;

import chungbazi.chungbazi_be.domain.auth.jwt.SecurityUtils;
import chungbazi.chungbazi_be.domain.character.converter.CharacterConverter;
import chungbazi.chungbazi_be.domain.character.dto.CharacterResponseDTO;
import chungbazi.chungbazi_be.domain.character.entity.Character;
import chungbazi.chungbazi_be.domain.character.repository.CharacterRepository;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.repository.UserRepository;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
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
}
