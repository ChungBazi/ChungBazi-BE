package chungbazi.chungbazi_be.domain.chatbot.dto;

import chungbazi.chungbazi_be.domain.policy.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class ChatBotResponseDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class PolicyDto {
        Long policyId;
        String title;
        Category category; //추후 추가
    }
}
