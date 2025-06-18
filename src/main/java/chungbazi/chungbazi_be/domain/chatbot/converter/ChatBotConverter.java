package chungbazi.chungbazi_be.domain.chatbot.converter;

import chungbazi.chungbazi_be.domain.chatbot.dto.ChatBotResponseDTO;
import chungbazi.chungbazi_be.domain.policy.entity.Policy;
import java.util.List;

public class ChatBotConverter {
    public static List<ChatBotResponseDTO.PolicyDto> toPolicyListDto(List<Policy> policies) {
        return policies.stream()
                .map(policy ->
                    ChatBotResponseDTO.PolicyDto.builder()
                            .policyId(policy.getId())
                            .title(policy.getName())
                            .category(policy.getCategory())
                            .build()
                ).toList();
    }

    public static ChatBotResponseDTO.ChatDto toChatDto(String answer){
        return ChatBotResponseDTO.ChatDto.builder().answer(answer).build();
    }
}
