package chungbazi.chungbazi_be.domain.community.service;

import chungbazi.chungbazi_be.domain.auth.jwt.SecurityUtils;
import chungbazi.chungbazi_be.domain.notification.entity.Notification;
import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;
import chungbazi.chungbazi_be.domain.notification.repository.NotificationRepository;
import chungbazi.chungbazi_be.domain.notification.service.FCMTokenService;
import chungbazi.chungbazi_be.domain.notification.service.NotificationService;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.repository.UserRepository;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RewardService {
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final FCMTokenService fcmTokenService;
    private final NotificationService notificationService;

    //리워드 체크
    public void checkRewards() {
        User user=userRepository.findById(SecurityUtils.getUserId())
                .orElseThrow(()-> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));

        // 리워드 레벨별 필요한 게시글/댓글 개수를 배열로 정의
        int[] thresholds = {3, 5, 7, 15, 20, 25, 30, 35, 40};  // 최대 리워드 레벨은 10

        int currentReward = user.getReward();

        // 최대 리워드 레벨 도달 여부 체크
        if (currentReward < 10) {
            int requiredCount = thresholds[currentReward - 1];

            if (user.getPosts().size() >= requiredCount && user.getComments().size() >= requiredCount) {
                int nextReward = currentReward + 1;
                user.updateReward(nextReward);
                sendRewardNotification(nextReward);
            }
        }
        userRepository.save(user);

    }

    private void sendRewardNotification(int reward) {
        User user=userRepository.findById(SecurityUtils.getUserId())
                .orElseThrow(()-> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));

        Notification notification=Notification.builder()
                .user(user)
                .type(NotificationType.REWARD_ALARM)
                .message(reward+"단계에 달성하여 캐릭터가 지급되었습니다.")
                .isRead(false)
                .build();

        notificationRepository.save(notification);

        //FCM푸시 전송
        String fcmToken = fcmTokenService.getToken(user.getId());
        if(fcmToken!=null){
            notificationService.pushFCMNotification(fcmToken,notification.getType(),notification.getMessage());
        }
    }
}
