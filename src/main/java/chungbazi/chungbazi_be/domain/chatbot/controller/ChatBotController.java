package chungbazi.chungbazi_be.domain.chatbot.controller;

import chungbazi.chungbazi_be.domain.chatbot.dto.ChatBotResponseDTO;
import chungbazi.chungbazi_be.domain.chatbot.service.ChatBotService;
import chungbazi.chungbazi_be.domain.policy.entity.Category;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatbot")
@Validated
public class ChatBotController {
    private final ChatBotService chatBotService;

    @GetMapping("/policies")
    @Operation(summary = "정책 찾기 API", description = "카테고리별 정책 찾기 API")
    public ApiResponse<List<ChatBotResponseDTO.PolicyDto>> getCategoryPolicies(
            @RequestParam(value = "category")Category category
            ) {
        return ApiResponse.onSuccess(chatBotService.getPolicies(category));
    }
}
