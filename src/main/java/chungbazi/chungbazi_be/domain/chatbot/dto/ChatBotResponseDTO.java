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
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PolicyDetailDto {
        Long policyId;
        String title;
        Category category;
        String intro;
        String bizId; //신청 기간 추가
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ChatDto {
        String answer;
    }
}
