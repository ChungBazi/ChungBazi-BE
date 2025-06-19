package chungbazi.chungbazi_be.domain.chat.controller;

import chungbazi.chungbazi_be.domain.chat.dto.ChatRequestDTO;
import chungbazi.chungbazi_be.domain.chat.dto.ChatResponseDTO;
import chungbazi.chungbazi_be.domain.chat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/chat/{postId}/create-room")
    @Operation(summary = "채팅방 생성 API", description = "채팅방을 생성하는 API입니다.")
    public ChatResponseDTO.createChatRoomResponse createChatRoom(@PathVariable Long postId){
        return chatService.createChatRoom(postId);
    }

    @MessageMapping("/chat.message.{chatRoomId}")
    public void sendMessage(@DestinationVariable Long chatRoomId, ChatRequestDTO.messagedto dto) {
        log.info("sendMessage: chatRoomId={}, message={}", chatRoomId, dto.getContent());
        ChatResponseDTO.messageResponse response = chatService.sendMessage(chatRoomId, dto);

    }

}
