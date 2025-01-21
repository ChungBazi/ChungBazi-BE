package chungbazi.chungbazi_be.domain.member.entity.mapping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberAddition is a Querydsl query type for MemberAddition
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberAddition extends EntityPathBase<MemberAddition> {

    private static final long serialVersionUID = 1957486827L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberAddition memberAddition = new QMemberAddition("memberAddition");

    public final chungbazi.chungbazi_be.domain.member.entity.QAddition addition;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final chungbazi.chungbazi_be.domain.member.entity.QMember member;

    public QMemberAddition(String variable) {
        this(MemberAddition.class, forVariable(variable), INITS);
    }

    public QMemberAddition(Path<? extends MemberAddition> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberAddition(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberAddition(PathMetadata metadata, PathInits inits) {
        this(MemberAddition.class, metadata, inits);
    }

    public QMemberAddition(Class<? extends MemberAddition> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.addition = inits.isInitialized("addition") ? new chungbazi.chungbazi_be.domain.member.entity.QAddition(forProperty("addition")) : null;
        this.member = inits.isInitialized("member") ? new chungbazi.chungbazi_be.domain.member.entity.QMember(forProperty("member")) : null;
    }

}

