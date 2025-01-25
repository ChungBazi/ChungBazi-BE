package chungbazi.chungbazi_be.domain.notification.service;

import chungbazi.chungbazi_be.domain.notification.dto.NotificationRequestDTO;
import chungbazi.chungbazi_be.domain.notification.dto.NotificationResponseDTO;
import chungbazi.chungbazi_be.domain.notification.entity.Notification;
import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;
import chungbazi.chungbazi_be.domain.notification.repository.NotificationRepository;
import chungbazi.chungbazi_be.domain.user.UserHandler;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.repository.UserRepository;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final FCMTokenService fcmTokenService;

    public NotificationResponseDTO.responseDto sendNotification(NotificationRequestDTO.createDTO dto) {
        //알림 생성
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(()->new UserHandler(ErrorStatus.NOT_FOUND_USER));
        Notification notification=Notification.builder()
                .user(user)
                .type(dto.getType())
                .message(dto.getMessage())
                .isRead(false)
                .build();

        notificationRepository.save(notification);

        //FCM 푸시 전송
        String fcmToken= fcmTokenService.getToken(user.getId());
        if(fcmToken!=null){
            pushFCMNotification(fcmToken,dto.getType(),dto.getMessage());
        }
        return NotificationResponseDTO.responseDto.builder()
                .notificationId(notification.getId())
                .createdAt(notification.getCreatedAt())
                .build();
    }

    //fcm한테 알림 요청
    private void pushFCMNotification(String fcmToken,NotificationType type,String message) {
        try {
            com.google.firebase.messaging.Notification notification =
                    com.google.firebase.messaging.Notification.builder()
                            .setTitle("새로운 알림이 도착했습니다.")
                            .setBody("[" + type.toString() + "]" + message)
                            .build();

            Message firebaseMessage = Message.builder()
                    .setToken(fcmToken)
                    .setNotification(notification)
                    .build();

            String response = FirebaseMessaging.getInstance().send(firebaseMessage);
            System.out.println("FCM 전송 성공: " + response);
        }catch(FirebaseMessagingException e){
            e.printStackTrace();
        }
    }

}
