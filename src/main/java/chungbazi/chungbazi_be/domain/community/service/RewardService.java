package chungbazi.chungbazi_be.domain.community.service;

import chungbazi.chungbazi_be.domain.auth.jwt.SecurityUtils;
import chungbazi.chungbazi_be.domain.community.repository.CommentRepository;
import chungbazi.chungbazi_be.domain.community.repository.PostRepository;
import chungbazi.chungbazi_be.domain.notification.entity.Notification;
import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;
import chungbazi.chungbazi_be.domain.notification.repository.NotificationRepository;
import chungbazi.chungbazi_be.domain.notification.service.FCMTokenService;
import chungbazi.chungbazi_be.domain.notification.service.NotificationService;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.entity.enums.RewardLevel;
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
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public void checkRewards() {
        User user = userRepository.findById(SecurityUtils.getUserId())
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));

        int currentReward = user.getReward().getLevel();

        if (currentReward < RewardLevel.LEVEL_10.getLevel()) {
            RewardLevel nextRewardLevel = RewardLevel.getNextRewardLevel(currentReward);
            if (nextRewardLevel != null) {
                int requiredCount = nextRewardLevel.getThreashold();
                int postCount = postRepository.countPostByAuthorId(user.getId());
                int commentCount = commentRepository.countCommentByAuthorId(user.getId());

                if (postCount >= requiredCount && commentCount >= requiredCount) {
                    user.updateRewardLevel(nextRewardLevel);

                    if (user.getNotificationSetting().isRewardAlarm()){
                        sendRewardNotification(nextRewardLevel.getLevel());
                    }
                }
            }
        }
        userRepository.save(user);
    }

    private void sendRewardNotification(int rewardLevel) {
        User user=userRepository.findById(SecurityUtils.getUserId())
                .orElseThrow(()-> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));

        Notification notification=Notification.builder()
                .user(user)
                .type(NotificationType.REWARD_ALARM)
                .message(rewardLevel+"단계에 달성하여 캐릭터가 지급되었습니다.")
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
