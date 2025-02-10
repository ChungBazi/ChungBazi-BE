package chungbazi.chungbazi_be.domain.character.controller;

import chungbazi.chungbazi_be.domain.character.dto.CharacterResponseDTO;
import chungbazi.chungbazi_be.domain.character.service.CharacterService;
import chungbazi.chungbazi_be.domain.user.entity.enums.RewardLevel;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import com.google.protobuf.Api;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/character")
public class CharacterController {
    private final CharacterService characterService;
    @GetMapping("/character-list")
    @Operation(summary = "캐릭터 조회 API", description = "마이페이지 캐릭터 조회")
    public ApiResponse<List<CharacterResponseDTO.CharacterListDto>> getCharacters() {
        return ApiResponse.onSuccess(characterService.getCharacters());
    }

    @PatchMapping("/select-or-open")
    @Operation(summary = "캐릭터 선택 및 오픈 API",
            description = """
                캐릭터 선택 및 오픈
                - 레벨 값 예시 : LEVEL_1 (10까지 가능)
                """)
    public ApiResponse<CharacterResponseDTO.MainCharacterDto> selectOrOpen(@RequestParam RewardLevel selectedLevel) {
        return ApiResponse.onSuccess(characterService.selectOrOpen(selectedLevel));
    }

    @GetMapping("/main-character")
    @Operation(summary = "메인 캐릭터 조회 API", description = "메인 캐릭터 조회")
    public ApiResponse<CharacterResponseDTO.MainCharacterDto> getMainCharacter() {
        return ApiResponse.onSuccess(characterService.getMainCharacter());
    }
}
