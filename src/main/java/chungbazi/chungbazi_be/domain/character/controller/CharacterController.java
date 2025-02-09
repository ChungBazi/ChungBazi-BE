package chungbazi.chungbazi_be.domain.character.controller;

import chungbazi.chungbazi_be.domain.character.dto.CharacterResponseDTO;
import chungbazi.chungbazi_be.domain.character.service.CharacterService;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import com.google.protobuf.Api;
import io.swagger.v3.oas.annotations.Operation;
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
    @GetMapping("/characterList")
    @Operation(summary = "캐릭터 조회 API", description = "마이페이지 캐릭터 조회")
    public ApiResponse<CharacterResponseDTO.CharacterListDto> getCharacters() {
        return ApiResponse.onSuccess(characterService.getCharacters());
    }

    @PatchMapping("/selectOrOpen")
    @Operation(summary = "캐릭터 선택 및 오픈 API", description = "캐릭터 선택 및 오픈")
    public ApiResponse<CharacterResponseDTO.MainCharacterDto> selectOrOpen(@RequestParam String selectedLevel) {
        return ApiResponse.onSuccess(characterService.selectOrOpen(selectedLevel));
    }

    @GetMapping("/mainCharacter")
    @Operation(summary = "메인 캐릭터 조회 API", description = "메인 캐릭터 조회")
    public ApiResponse<CharacterResponseDTO.MainCharacterDto> getMainCharacter() {
        return ApiResponse.onSuccess(characterService.getMainCharacter());
    }
}
