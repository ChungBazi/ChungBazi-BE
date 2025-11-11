package chungbazi.chungbazi_be.domain.chat.repository.ChatRoomRepository;

import chungbazi.chungbazi_be.domain.chat.entity.ChatRoom;
import chungbazi.chungbazi_be.domain.chat.entity.QChatRoom;
import chungbazi.chungbazi_be.domain.chat.entity.QChatRoomSetting;
import chungbazi.chungbazi_be.domain.user.entity.QUserBlock;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryCustomImpl implements ChatRoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChatRoom> findRoomsByUserIdAndIsActive(Long userId, Boolean active) {
        QChatRoom qChatRoom = QChatRoom.chatRoom;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(qChatRoom.chatRoomSettings.any().user.id.eq(userId));

        if (active != null) {
            booleanBuilder.and(qChatRoom.isActive.eq(active));
        }

        return queryFactory
                .selectFrom(qChatRoom)
                .where(booleanBuilder)
                .orderBy(qChatRoom.createdAt.desc())
                .fetch();
    }

    @Override
    public List<ChatRoom> findBlockedChatRoomsByUserId(Long userId){
        QChatRoom qChatRoom = QChatRoom.chatRoom;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QUserBlock qUserBlock = QUserBlock.userBlock;
        QChatRoomSetting qCurrentSetting = QChatRoomSetting.chatRoomSetting;
        QChatRoomSetting qOtherSetting = new QChatRoomSetting("otherSetting");

        BooleanExpression currentUser= qCurrentSetting.user.id.eq(userId).and(qCurrentSetting.chatRoom.eq(qChatRoom));
        BooleanExpression otherBlockedUser = qOtherSetting.user.id.eq(qUserBlock.blocked.id).and(qOtherSetting.chatRoom.eq(qChatRoom));

        return queryFactory
                .select(qChatRoom)
                .distinct()
                .from(qChatRoom)
                .innerJoin(qChatRoom.chatRoomSettings, qCurrentSetting)
                .innerJoin(qChatRoom.chatRoomSettings, qOtherSetting)
                .innerJoin(qUserBlock).on(
                        qUserBlock.blocker.id.eq(userId),
                        qUserBlock.isActive.isTrue(),
                        qUserBlock.blocked.id.eq(qOtherSetting.user.id)
                )
                .where(currentUser.and(otherBlockedUser))
                .orderBy(qChatRoom.createdAt.desc())
                .fetch();

    }

    @Override
    public List<ChatRoom> findActiveRoomsByUserId(Long userId) {
        QChatRoom qChatRoom = QChatRoom.chatRoom;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qChatRoom.chatRoomSettings.any().user.id.eq(userId));
        booleanBuilder.and(qChatRoom.isActive.isTrue());

        List<ChatRoom> chatRooms = queryFactory
                .selectFrom(qChatRoom)
                .where(booleanBuilder)
                .orderBy(qChatRoom.createdAt.desc())
                .fetch();

        return chatRooms;
    }

}
