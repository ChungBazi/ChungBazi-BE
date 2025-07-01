package chungbazi.chungbazi_be.domain.notification.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoomSetting is a Querydsl query type for ChatRoomSetting
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoomSetting extends EntityPathBase<ChatRoomSetting> {

    private static final long serialVersionUID = 595988759L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatRoomSetting chatRoomSetting = new QChatRoomSetting("chatRoomSetting");

    public final chungbazi.chungbazi_be.global.entity.QBaseTimeEntity _super = new chungbazi.chungbazi_be.global.entity.QBaseTimeEntity(this);

    public final chungbazi.chungbazi_be.domain.chat.entity.QChatRoom chatRoom;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath enabled = createBoolean("enabled");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final chungbazi.chungbazi_be.domain.user.entity.QUser user;

    public QChatRoomSetting(String variable) {
        this(ChatRoomSetting.class, forVariable(variable), INITS);
    }

    public QChatRoomSetting(Path<? extends ChatRoomSetting> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatRoomSetting(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatRoomSetting(PathMetadata metadata, PathInits inits) {
        this(ChatRoomSetting.class, metadata, inits);
    }

    public QChatRoomSetting(Class<? extends ChatRoomSetting> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatRoom = inits.isInitialized("chatRoom") ? new chungbazi.chungbazi_be.domain.chat.entity.QChatRoom(forProperty("chatRoom"), inits.get("chatRoom")) : null;
        this.user = inits.isInitialized("user") ? new chungbazi.chungbazi_be.domain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

