package chungbazi.chungbazi_be.domain.chat.service;

import chungbazi.chungbazi_be.domain.chat.entity.ChatRoom;

import chungbazi.chungbazi_be.domain.chat.repository.chatRoom.ChatRoomRepository;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoom getChatRoomById(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()->new NotFoundHandler(ErrorStatus.NOT_FOUND_CHATROOM));

        return chatRoom;
    }
}
