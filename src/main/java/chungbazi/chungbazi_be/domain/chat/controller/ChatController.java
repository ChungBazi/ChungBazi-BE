package chungbazi.chungbazi_be.domain.chat.controller;

import chungbazi.chungbazi_be.domain.chat.dto.ChatRequestDTO;
import chungbazi.chungbazi_be.domain.chat.dto.ChatResponseDTO;
import chungbazi.chungbazi_be.domain.chat.entity.ChatRoom;
import chungbazi.chungbazi_be.domain.chat.service.ChatService;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static chungbazi.chungbazi_be.domain.chat.entity.QChatRoom.chatRoom;

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
        chatService.sendMessage(chatRoomId, dto);

    }

    @GetMapping("/rooms/{chatRoomId}")
    @Operation(summary = "채팅방 상세 조회",description = "채팅방의 상세 정보와 메세지들을 조회합니다.")
    public ApiResponse<ChatResponseDTO.chatRoomResponse> getChatRoomDetail(@PathVariable Long chatRoomId, @RequestParam(required = false) Long cursorId, @RequestParam(defaultValue = "10") int limit) {
        return ApiResponse.onSuccess(chatService.getChatRoomDetail(chatRoomId,cursorId,limit));
    }

    @DeleteMapping("/rooms/{chatRoomId}/leave")
    @Operation(summary = "채팅방 나가기", description = "채팅방에서 나가는 API입니다.")
    public ApiResponse<Void> leaveChatRoom(
            @PathVariable Long chatRoomId) {

        chatService.leaveChatRoom(chatRoomId);
        return ApiResponse.onSuccess(null);
    }

}
