package chungbazi.chungbazi_be.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 724966575L;

    public static final QMember member = new QMember("member1");

    public final StringPath birthDay = createString("birthDay");

    public final StringPath birthYear = createString("birthYear");

    public final EnumPath<chungbazi.chungbazi_be.domain.member.entity.enums.Education> education = createEnum("education", chungbazi.chungbazi_be.domain.member.entity.enums.Education.class);

    public final StringPath email = createString("email");

    public final EnumPath<chungbazi.chungbazi_be.domain.member.entity.enums.Employment> employment = createEnum("employment", chungbazi.chungbazi_be.domain.member.entity.enums.Employment.class);

    public final EnumPath<chungbazi.chungbazi_be.domain.member.entity.enums.Gender> gender = createEnum("gender", chungbazi.chungbazi_be.domain.member.entity.enums.Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final EnumPath<chungbazi.chungbazi_be.domain.member.entity.enums.Income> income = createEnum("income", chungbazi.chungbazi_be.domain.member.entity.enums.Income.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final ListPath<chungbazi.chungbazi_be.domain.member.entity.mapping.MemberAddition, chungbazi.chungbazi_be.domain.member.entity.mapping.QMemberAddition> memberAdditionList = this.<chungbazi.chungbazi_be.domain.member.entity.mapping.MemberAddition, chungbazi.chungbazi_be.domain.member.entity.mapping.QMemberAddition>createList("memberAdditionList", chungbazi.chungbazi_be.domain.member.entity.mapping.MemberAddition.class, chungbazi.chungbazi_be.domain.member.entity.mapping.QMemberAddition.class, PathInits.DIRECT2);

    public final ListPath<chungbazi.chungbazi_be.domain.member.entity.mapping.MemberInterest, chungbazi.chungbazi_be.domain.member.entity.mapping.QMemberInterest> memberInterestList = this.<chungbazi.chungbazi_be.domain.member.entity.mapping.MemberInterest, chungbazi.chungbazi_be.domain.member.entity.mapping.QMemberInterest>createList("memberInterestList", chungbazi.chungbazi_be.domain.member.entity.mapping.MemberInterest.class, chungbazi.chungbazi_be.domain.member.entity.mapping.QMemberInterest.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final EnumPath<chungbazi.chungbazi_be.domain.member.entity.enums.OAuthProvider> oAuthProvider = createEnum("oAuthProvider", chungbazi.chungbazi_be.domain.member.entity.enums.OAuthProvider.class);

    public final EnumPath<chungbazi.chungbazi_be.domain.member.entity.enums.Region> region = createEnum("region", chungbazi.chungbazi_be.domain.member.entity.enums.Region.class);

    public final NumberPath<Integer> reward = createNumber("reward", Integer.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

