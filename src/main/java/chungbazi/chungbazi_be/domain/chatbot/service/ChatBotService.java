package chungbazi.chungbazi_be.domain.chatbot.service;

import chungbazi.chungbazi_be.domain.chatbot.converter.ChatBotConverter;
import chungbazi.chungbazi_be.domain.chatbot.dto.ChatBotResponseDTO;
import chungbazi.chungbazi_be.domain.policy.entity.Category;
import chungbazi.chungbazi_be.domain.policy.entity.Policy;
import chungbazi.chungbazi_be.domain.policy.repository.PolicyRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatBotService {
    private final PolicyRepository policyRepository;
    private final ChatGptClient chatGptClient;

    public List<ChatBotResponseDTO.PolicyDto> getPolicies(Category category) {
        List<Policy> policies = policyRepository.findTop5ByCategoryOrderByCreatedAtDesc(category);
        return ChatBotConverter.toPolicyListDto(policies);
    }

    public ChatBotResponseDTO.ChatDto askGpt(String userMessage){
        String systemPrompt = "당신은 청년 정책을 설명해주는 챗봇입니다."; // 프롬프트 정적 지정
        String answer = chatGptClient.askChatGpt(userMessage, systemPrompt);
        return ChatBotConverter.toChatDto(answer);
    }

}
