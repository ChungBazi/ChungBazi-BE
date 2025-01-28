package chungbazi.chungbazi_be.domain.notification.repository;

import chungbazi.chungbazi_be.domain.notification.entity.Notification;
import chungbazi.chungbazi_be.domain.notification.entity.QNotification;
import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;
import com.google.api.gax.paging.Page;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private final QNotification qNotification=QNotification.notification;


        //알림 읽음 처리
    @Override
    public void markAllAsRead(Long userId, NotificationType type){
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qNotification.user.id.eq(userId));
        booleanBuilder.and(qNotification.isRead.eq(false));

        if (type!=null){
            booleanBuilder.and(qNotification.type.eq(type));
        }

        queryFactory.update(qNotification)
                .set(qNotification.isRead,true)
                .where(booleanBuilder)
                .execute();
    }
}
