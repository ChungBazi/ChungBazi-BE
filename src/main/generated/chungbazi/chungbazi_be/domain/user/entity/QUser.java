package chungbazi.chungbazi_be.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 305651281L;

    public static final QUser user = new QUser("user");

    public final EnumPath<chungbazi.chungbazi_be.domain.user.entity.enums.Education> education = createEnum("education", chungbazi.chungbazi_be.domain.user.entity.enums.Education.class);

    public final StringPath email = createString("email");

    public final EnumPath<chungbazi.chungbazi_be.domain.user.entity.enums.Employment> employment = createEnum("employment", chungbazi.chungbazi_be.domain.user.entity.enums.Employment.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final EnumPath<chungbazi.chungbazi_be.domain.user.entity.enums.Income> income = createEnum("income", chungbazi.chungbazi_be.domain.user.entity.enums.Income.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final StringPath name = createString("name");

    public final ListPath<chungbazi.chungbazi_be.domain.notification.entity.Notification, chungbazi.chungbazi_be.domain.notification.entity.QNotification> notificationList = this.<chungbazi.chungbazi_be.domain.notification.entity.Notification, chungbazi.chungbazi_be.domain.notification.entity.QNotification>createList("notificationList", chungbazi.chungbazi_be.domain.notification.entity.Notification.class, chungbazi.chungbazi_be.domain.notification.entity.QNotification.class, PathInits.DIRECT2);

    public final EnumPath<chungbazi.chungbazi_be.domain.user.entity.enums.OAuthProvider> oAuthProvider = createEnum("oAuthProvider", chungbazi.chungbazi_be.domain.user.entity.enums.OAuthProvider.class);

    public final EnumPath<chungbazi.chungbazi_be.domain.user.entity.enums.Region> region = createEnum("region", chungbazi.chungbazi_be.domain.user.entity.enums.Region.class);

    public final NumberPath<Integer> reward = createNumber("reward", Integer.class);

    public final BooleanPath surveyStatus = createBoolean("surveyStatus");

    public final ListPath<chungbazi.chungbazi_be.domain.user.entity.mapping.UserAddition, chungbazi.chungbazi_be.domain.user.entity.mapping.QUserAddition> userAdditionList = this.<chungbazi.chungbazi_be.domain.user.entity.mapping.UserAddition, chungbazi.chungbazi_be.domain.user.entity.mapping.QUserAddition>createList("userAdditionList", chungbazi.chungbazi_be.domain.user.entity.mapping.UserAddition.class, chungbazi.chungbazi_be.domain.user.entity.mapping.QUserAddition.class, PathInits.DIRECT2);

    public final ListPath<chungbazi.chungbazi_be.domain.user.entity.mapping.UserInterest, chungbazi.chungbazi_be.domain.user.entity.mapping.QUserInterest> userInterestList = this.<chungbazi.chungbazi_be.domain.user.entity.mapping.UserInterest, chungbazi.chungbazi_be.domain.user.entity.mapping.QUserInterest>createList("userInterestList", chungbazi.chungbazi_be.domain.user.entity.mapping.UserInterest.class, chungbazi.chungbazi_be.domain.user.entity.mapping.QUserInterest.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

