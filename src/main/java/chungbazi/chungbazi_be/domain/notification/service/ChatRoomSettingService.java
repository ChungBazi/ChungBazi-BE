package chungbazi.chungbazi_be.domain.notification.service;

import chungbazi.chungbazi_be.domain.chat.entity.ChatRoom;
import chungbazi.chungbazi_be.domain.chat.repository.ChatRoomRepository.ChatRoomRepository;
import chungbazi.chungbazi_be.domain.notification.entity.ChatRoomSetting;
import chungbazi.chungbazi_be.domain.notification.repository.ChatRoomSettingRepository;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.repository.UserRepository;
import chungbazi.chungbazi_be.domain.user.utils.UserHelper;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomSettingService {
    private final ChatRoomSettingRepository chatRoomSettingRepository;
    private final UserHelper userHelper;
    private final ChatRoomRepository chatRoomRepository;

    public boolean getChatRoomSettingIsEnabled(Long userId, Long roomId) {
        ChatRoomSetting chatRoomSetting= chatRoomSettingRepository.findByUserIdAndChatRoomId(userId,roomId)
                .orElseThrow(()->new NotFoundHandler(ErrorStatus.NOT_FOUND_CHATROOMSETTING));

        return chatRoomSetting.isEnabled();
    }

    public void setChatRoomSettingIsEnabled(Long chatRoomId, boolean enabled) {
        User user = userHelper.getAuthenticatedUser();

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()->new NotFoundHandler(ErrorStatus.NOT_FOUND_CHATROOM));

        ChatRoomSetting chatRoomSetting= chatRoomSettingRepository.findByUserIdAndChatRoomId(user.getId(),chatRoomId)
                .orElseThrow(()->new NotFoundHandler(ErrorStatus.NOT_FOUND_CHATROOMSETTING));

        chatRoomSetting.updateChatNotification(enabled);
        chatRoomSettingRepository.save(chatRoomSetting);
    }

}
