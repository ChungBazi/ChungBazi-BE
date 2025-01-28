package chungbazi.chungbazi_be.domain.notification.repository;


import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;



public interface NotificationRepositoryCustom {

    void markAllAsRead(Long userId,NotificationType type);
}
