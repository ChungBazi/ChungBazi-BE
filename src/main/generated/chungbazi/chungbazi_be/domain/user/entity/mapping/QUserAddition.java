package chungbazi.chungbazi_be.domain.user.entity.mapping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserAddition is a Querydsl query type for UserAddition
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserAddition extends EntityPathBase<UserAddition> {

    private static final long serialVersionUID = 443131789L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserAddition userAddition = new QUserAddition("userAddition");

    public final chungbazi.chungbazi_be.domain.user.entity.QAddition addition;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final chungbazi.chungbazi_be.domain.user.entity.QUser user;

    public QUserAddition(String variable) {
        this(UserAddition.class, forVariable(variable), INITS);
    }

    public QUserAddition(Path<? extends UserAddition> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserAddition(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserAddition(PathMetadata metadata, PathInits inits) {
        this(UserAddition.class, metadata, inits);
    }

    public QUserAddition(Class<? extends UserAddition> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.addition = inits.isInitialized("addition") ? new chungbazi.chungbazi_be.domain.user.entity.QAddition(forProperty("addition")) : null;
        this.user = inits.isInitialized("user") ? new chungbazi.chungbazi_be.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

