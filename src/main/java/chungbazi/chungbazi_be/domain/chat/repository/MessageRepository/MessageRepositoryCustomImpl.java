package chungbazi.chungbazi_be.domain.chat.repository.MessageRepository;

import chungbazi.chungbazi_be.domain.chat.entity.Message;
import chungbazi.chungbazi_be.domain.chat.entity.QMessage;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryCustomImpl implements MessageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Message> findMessagesByChatRoomId(Long chatRoomId, Long cursor,int limit){
        QMessage qMessage = QMessage.message;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qMessage.chatRoom.id.eq(chatRoomId));

        if(cursor != null && cursor!=0){
            booleanBuilder.and(qMessage.id.lt(cursor));
        }

        List<Message> messages=queryFactory
                .selectFrom(qMessage)
                .where(booleanBuilder)
                .orderBy(qMessage.createdAt.desc())
                .limit(limit+1)
                .fetch();

        return messages;
    }

    @Override
    @Transactional
    public long markAllAsRead(Long chatRoomId, Long userId) {
        QMessage qMessage = QMessage.message;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qMessage.chatRoom.id.eq(chatRoomId));
        booleanBuilder.and(qMessage.sender.id.ne(userId));
        booleanBuilder.and(qMessage.isRead.eq(false));

        long updated=queryFactory.update(qMessage)
                .set(qMessage.isRead, true)
                .where(booleanBuilder)
                .execute();
        return updated;
    }
}
