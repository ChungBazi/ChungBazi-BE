package chungbazi.chungbazi_be.domain.community.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommentHeart is a Querydsl query type for CommentHeart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommentHeart extends EntityPathBase<CommentHeart> {

    private static final long serialVersionUID = 1942687695L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommentHeart commentHeart = new QCommentHeart("commentHeart");

    public final chungbazi.chungbazi_be.global.entity.QBaseTimeEntity _super = new chungbazi.chungbazi_be.global.entity.QBaseTimeEntity(this);

    public final QComment comment;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final chungbazi.chungbazi_be.domain.user.entity.QUser user;

    public QCommentHeart(String variable) {
        this(CommentHeart.class, forVariable(variable), INITS);
    }

    public QCommentHeart(Path<? extends CommentHeart> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommentHeart(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommentHeart(PathMetadata metadata, PathInits inits) {
        this(CommentHeart.class, metadata, inits);
    }

    public QCommentHeart(Class<? extends CommentHeart> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new QComment(forProperty("comment"), inits.get("comment")) : null;
        this.user = inits.isInitialized("user") ? new chungbazi.chungbazi_be.domain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

