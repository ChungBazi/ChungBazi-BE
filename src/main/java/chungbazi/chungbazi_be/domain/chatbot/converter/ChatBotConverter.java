package chungbazi.chungbazi_be.domain.chatbot.converter;

import chungbazi.chungbazi_be.domain.chatbot.dto.ChatBotResponseDTO;
import chungbazi.chungbazi_be.domain.policy.entity.Policy;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class ChatBotConverter {
    public static ChatBotResponseDTO.PolicyDto toPolicyDto(Policy policy, LocalDate today, Set<String> validKeywords) {
        return ChatBotResponseDTO.PolicyDto.builder()
                .policyId(policy.getId())
                .title(policy.getName())
                .status(policy.getStatus(today, validKeywords))
                .build();
    }

    public static ChatBotResponseDTO.PolicyDetailDto toPolicyDetailDto(Policy policy, LocalDate today, Set<String> validKeywords) {
        return ChatBotResponseDTO.PolicyDetailDto.builder()
                .policyId(policy.getId())
                .title(policy.getName())
                .category(policy.getCategory())
                .intro(policy.getIntro())
                .bizId(policy.getBizId())
                .status(policy.getStatus(today, validKeywords))
                .build();
    }

    public static ChatBotResponseDTO.ChatDto toChatDto(String answer){
        return ChatBotResponseDTO.ChatDto.builder().answer(answer).build();
    }
}
