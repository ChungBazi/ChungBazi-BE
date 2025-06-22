package chungbazi.chungbazi_be.domain.user.repository.UserBlockRepository;

import chungbazi.chungbazi_be.domain.user.entity.QUserBlock;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserBlockRepositoryCustomImpl implements UserBlockRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsBlockBetweenUsers(Long user1Id, Long user2Id) {
        QUserBlock qUserBlock = QUserBlock.userBlock;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qUserBlock.isActive.eq(true));
        booleanBuilder.and(qUserBlock.blocker.id.in(user1Id, user2Id));
        booleanBuilder.and(qUserBlock.blocked.id.in(user1Id, user2Id));

        return queryFactory
                .selectOne()
                .from(qUserBlock)
                .where(booleanBuilder)
                .fetchFirst()!=null;
    }
}
