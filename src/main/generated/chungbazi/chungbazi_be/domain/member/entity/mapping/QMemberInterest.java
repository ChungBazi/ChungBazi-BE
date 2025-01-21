package chungbazi.chungbazi_be.domain.member.entity.mapping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberInterest is a Querydsl query type for MemberInterest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberInterest extends EntityPathBase<MemberInterest> {

    private static final long serialVersionUID = -540488423L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberInterest memberInterest = new QMemberInterest("memberInterest");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final chungbazi.chungbazi_be.domain.member.entity.QInterest interest;

    public final chungbazi.chungbazi_be.domain.member.entity.QMember member;

    public QMemberInterest(String variable) {
        this(MemberInterest.class, forVariable(variable), INITS);
    }

    public QMemberInterest(Path<? extends MemberInterest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberInterest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberInterest(PathMetadata metadata, PathInits inits) {
        this(MemberInterest.class, metadata, inits);
    }

    public QMemberInterest(Class<? extends MemberInterest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.interest = inits.isInitialized("interest") ? new chungbazi.chungbazi_be.domain.member.entity.QInterest(forProperty("interest")) : null;
        this.member = inits.isInitialized("member") ? new chungbazi.chungbazi_be.domain.member.entity.QMember(forProperty("member")) : null;
    }

}

