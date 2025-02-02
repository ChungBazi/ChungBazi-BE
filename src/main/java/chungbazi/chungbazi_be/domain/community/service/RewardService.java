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

        switch (user.getReward()){
            case 1:
                if (user.getPosts().size()>=3 && user.getComments().size()>=3){
                    user.updateReward(2);
                    sendRewardNotification(2);
                }
                break;
            case 2:
                if (user.getPosts().size()>=5 && user.getComments().size()>=5){
                    user.updateReward(3);
                    sendRewardNotification(3);
                }
                break;
            case 3:
                if (user.getPosts().size()>=7 && user.getComments().size()>=7){
                    user.updateReward(4);
                    sendRewardNotification(4);
                }
                break;
            case 4:
                if (user.getPosts().size()>=15 && user.getComments().size()>=15){
                    user.updateReward(5);
                    sendRewardNotification(5);
                }
                break;
            case 5:
                if (user.getPosts().size()>=20 && user.getComments().size()>=20){
                    user.updateReward(6);
                    sendRewardNotification(6);
                }
                break;
            case 6:
                if (user.getPosts().size()>=25 && user.getComments().size()>=25){
                    user.updateReward(7);
                    sendRewardNotification(7);
                }
                break;
            case 7:
                if (user.getPosts().size()>=30 && user.getComments().size()>=30){
                    user.updateReward(8);
                    sendRewardNotification(8);
                }
                break;
            case 8:
                if (user.getPosts().size()>=35 && user.getComments().size()>=35){
                    user.updateReward(9);
                    sendRewardNotification(9);
                }
                break;
            case 9:
                if (user.getPosts().size()>=40 && user.getComments().size()>=40){
                    user.updateReward(10);
                    sendRewardNotification(10);
                }
                break;
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
