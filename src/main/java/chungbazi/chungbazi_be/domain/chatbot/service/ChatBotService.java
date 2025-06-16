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

    public List<ChatBotResponseDTO.PolicyDto> getPolicies(Category category) {
        List<Policy> policies = policyRepository.findTop5ByCategoryOrderByCreatedAtDesc(category);
        return ChatBotConverter.toPolicyListDto(policies);
    }

}
