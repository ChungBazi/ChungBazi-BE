package chungbazi.chungbazi_be.domain.notification.repository;

import chungbazi.chungbazi_be.domain.notification.entity.Notification;
import chungbazi.chungbazi_be.domain.notification.entity.QNotification;
import chungbazi.chungbazi_be.domain.notification.entity.enums.NotificationType;
import com.google.api.gax.paging.Page;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    //알림 읽음 처리
    @Override
    public void markAllAsRead(Long userId, NotificationType type){

        QNotification qNotification=QNotification.notification;

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

    //알림 조회
    @Override
    public List<Notification> findNotificationsByUserIdAndNotificationType(Long userId, NotificationType type, Long cursor, int limit) {

        QNotification qNotification=QNotification.notification;

        BooleanBuilder booleanBuilder=new BooleanBuilder();
        booleanBuilder.and(qNotification.user.id.eq(userId));

        //타입필터
        if(type!=null && !type.name().isEmpty()){
            booleanBuilder.and(qNotification.type.eq(type));
        }
        if(cursor!=null && cursor!=0){
            booleanBuilder.and(qNotification.id.lt(cursor));
        }

        //페이징 조회
        List<Notification> notifications=queryFactory
                .selectFrom(qNotification)
                .where(booleanBuilder)
                .orderBy(qNotification.createdAt.desc())
                .limit(limit)
                .fetch();

        return notifications;
    }


}
