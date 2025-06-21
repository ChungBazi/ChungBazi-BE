package chungbazi.chungbazi_be.domain.chat.repository.ChatRoomRepository;

import chungbazi.chungbazi_be.domain.chat.entity.ChatRoom;
import chungbazi.chungbazi_be.domain.chat.entity.QChatRoom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryCustomImpl implements ChatRoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChatRoom> findActiveRoomsByUserId(Long userId) {
        QChatRoom qChatRoom = QChatRoom.chatRoom;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qChatRoom.receiver.id.eq(userId).or(qChatRoom.sender.id.eq(userId)));
        booleanBuilder.and(qChatRoom.isActive.eq(true));

        List<ChatRoom> chatRooms = queryFactory
                .selectFrom(qChatRoom)
                .where(booleanBuilder)
                .orderBy(qChatRoom.createdAt.desc())
                .fetch();

        return chatRooms;
    }

}
