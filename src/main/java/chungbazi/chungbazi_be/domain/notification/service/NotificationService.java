package chungbazi.chungbazi_be.domain.notification.service;

import chungbazi.chungbazi_be.domain.notification.converter.NotificationConverter;
import chungbazi.chungbazi_be.domain.notification.dto.NotificationRequestDTO;
import chungbazi.chungbazi_be.domain.notification.dto.NotificationResponseDTO;
import chungbazi.chungbazi_be.domain.notification.entity.Notification;
import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;
import chungbazi.chungbazi_be.domain.notification.repository.NotificationRepository;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.repository.UserRepository;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                .orElseThrow(()->new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));
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

    //알람 읽음 처리
    public void markAsRead(Long notificationId){
        Notification notification=notificationRepository.findById(notificationId)
                .orElseThrow(()->new NotFoundHandler(ErrorStatus.NOT_FOUND_NOTIFICATION));
        notification.markAsRead();
    }

    //알림 조회
    public NotificationResponseDTO.notificationListDto getNotifications(Long userId, NotificationType type, Long cursor, int limit) {

        //알림 읽음 처리
        notificationRepository.markAllAsRead(userId, type);

        List<Notification> notificationList = notificationRepository.findNotificationsByUserIdAndNotificationType(userId, type, cursor, limit + 1);

        Long nextCursor = null;
        if (notificationList.size() > limit) {
            Notification lastNotification = notificationList.get(limit - 1);
            nextCursor = lastNotification.getId();
            notificationList = notificationList.subList(0, limit);
        }

        List<NotificationResponseDTO.notificationDto> notificationDtos=notificationList.stream()
                .map(notification -> NotificationConverter.toNotificationDto(notification))
                .collect(Collectors.toList());

        return NotificationResponseDTO.notificationListDto.builder()
                .notifications(notificationDtos)
                .nextCursor(nextCursor)
                .build();

    }


}
