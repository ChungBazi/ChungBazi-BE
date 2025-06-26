package chungbazi.chungbazi_be.domain.notification.service;

import chungbazi.chungbazi_be.domain.auth.jwt.SecurityUtils;
import chungbazi.chungbazi_be.domain.community.entity.Post;
import chungbazi.chungbazi_be.domain.notification.converter.NotificationConverter;
import chungbazi.chungbazi_be.domain.notification.dto.NotificationResponseDTO;
import chungbazi.chungbazi_be.domain.notification.dto.NotificationSettingReqDto;
import chungbazi.chungbazi_be.domain.notification.dto.NotificationSettingResDto;
import chungbazi.chungbazi_be.domain.notification.entity.Notification;
import chungbazi.chungbazi_be.domain.notification.entity.NotificationSetting;
import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;
import chungbazi.chungbazi_be.domain.notification.repository.NotificationRepository;
import chungbazi.chungbazi_be.domain.notification.repository.NotificationSettingRepository;
import chungbazi.chungbazi_be.domain.policy.entity.Policy;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.repository.UserRepository;
import chungbazi.chungbazi_be.domain.user.utils.UserHelper;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import chungbazi.chungbazi_be.global.utils.PaginationResult;
import chungbazi.chungbazi_be.global.utils.PaginationUtil;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class NotificationService {
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final FCMTokenService fcmTokenService;
    private final NotificationSettingRepository notificationSettingRepository;
    private final UserHelper userHelper;

    public NotificationResponseDTO.responseDto sendNotification(User user, NotificationType type, String message, Post post, Policy policy, chungbazi.chungbazi_be.domain.chat.entity.Message chat) {
        Notification notification=Notification.builder()
                .user(user)
                .type(type)
                .message(message)
                .isRead(false)
                .post(post)
                .policy(policy)
                .chat(chat)
                .build();

        notificationRepository.save(notification);

        //FCM 푸시 전송
        String fcmToken= fcmTokenService.getToken(user.getId());
        if(fcmToken!=null){
            Long policyId = (policy != null) ? policy.getId() : null;
            Long postId = (post != null) ? post.getId() : null;
            Long chatId = (chat != null) ? chat.getId() : null;

            pushFCMNotification(fcmToken,message,policyId, postId, chatId,type);
        }
        return NotificationResponseDTO.responseDto.builder()
                .notificationId(notification.getId())
                .createdAt(notification.getCreatedAt())
                .build();
    }

    //fcm한테 알림 요청
    public void pushFCMNotification(String fcmToken,String message,Long policyId, Long postId, Long chatId, NotificationType type) {
        try {
            com.google.firebase.messaging.Notification notification =
                    com.google.firebase.messaging.Notification.builder()
                            .setTitle("새로운 알림이 도착했습니다.")
                            .setBody(message)
                            .build();

            Map<String, String> data = new HashMap<>();
            if (policyId != null) {
                data.put("policyId", policyId.toString());
            }
            if (postId != null) {
                data.put("postId", postId.toString());
            }
            if (chatId != null) {
                data.put("chatId", chatId.toString());
            }
            data.put("notificationType", type.toString());

            Message firebaseMessage = Message.builder()
                    .setToken(fcmToken)
                    .setNotification(notification)
                    .putAllData(data)
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
    public NotificationResponseDTO.notificationListDto getNotifications(NotificationType type, Long cursor, int limit) {

        Long userId= SecurityUtils.getUserId();
        if(userId==null || userId <= 0){
            throw new NotFoundHandler(ErrorStatus.NOT_FOUND_USER);
        }

        //알림 읽음 처리
        notificationRepository.markAllAsRead(userId, type);

        List<Notification> notificationList = notificationRepository.findNotificationsByUserIdAndNotificationType(userId, type, cursor, limit + 1);

        PaginationResult<Notification> paginationResult = PaginationUtil.paginate(notificationList, limit);

        List<NotificationResponseDTO.notificationDto> notificationDtos=paginationResult.getItems().stream()
                .map(notification -> NotificationConverter.toNotificationDto(notification))
                .collect(Collectors.toList());

        return NotificationResponseDTO.notificationListDto.builder()
                .notifications(notificationDtos)
                .nextCursor(paginationResult.getNextCursor())
                .hasNext(paginationResult.isHasNext())
                .build();

    }

    //알림 수신 설정
    public NotificationSettingResDto.settingResDto setNotificationSetting(NotificationSettingReqDto dto){
        User user=userRepository.findById(SecurityUtils.getUserId())
                .orElseThrow(()->new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));

        NotificationSetting setting=user.getNotificationSetting();


        setting.updatePolicyAlarm(dto.isPolicyAlarm());
        setting.updateCommunityAlarm(dto.isCommunityAlarm());
        setting.updateRewardAlarm(dto.isRewardAlarm());
        setting.updateNoticeAlarm(dto.isNoticeAlarm());

        user.updateNotificationSetting(setting);
        notificationSettingRepository.save(setting);

        return NotificationConverter.toSettingResDto(setting);
    }

    //알림 수신 설정 조회
    public NotificationSettingResDto.settingResDto getNotificationSetting(){
        User user=userRepository.findById(SecurityUtils.getUserId())
                .orElseThrow(()->new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));
        NotificationSetting setting=user.getNotificationSetting();

        return NotificationConverter.toSettingResDto(setting);
    }

    //안 읽은 알림이 있는지 검사하는 로직
    public boolean isReadAllNotification(){
        User user=userHelper.getAuthenticatedUser();

        return user.getNotificationList().stream()
                .anyMatch(notification -> !notification.isRead());
    }


}
