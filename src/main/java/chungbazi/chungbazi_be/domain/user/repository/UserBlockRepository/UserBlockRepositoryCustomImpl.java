package chungbazi.chungbazi_be.domain.user.repository.UserBlockRepository;

import chungbazi.chungbazi_be.domain.user.entity.QUserBlock;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.entity.UserBlock;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class UserBlockRepositoryCustomImpl implements UserBlockRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

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

    @Override
    @Transactional
    public void block(Long blockerId, Long blockedId) {
        QUserBlock qUserBlock = QUserBlock.userBlock;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qUserBlock.blocker.id.eq(blockerId));
        booleanBuilder.and(qUserBlock.blocked.id.eq(blockedId));

        UserBlock existing = queryFactory
                .selectFrom(qUserBlock)
                .where(booleanBuilder)
                .fetchFirst();

        if(existing != null) {
            existing.setIsActive(true);
            return;
        }

        UserBlock userBlock = UserBlock.builder()
                .blocker(em.getReference(User.class, blockerId))
                .blocked(em.getReference(User.class, blockedId))
                .isActive(true)
                .build();
        em.persist(userBlock);
    }

    @Override
    @Transactional
    public void unblock(Long blockerId, Long blockedId) {
        QUserBlock qUserBlock = QUserBlock.userBlock;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qUserBlock.blocker.id.eq(blockerId));
        booleanBuilder.and(qUserBlock.blocked.id.eq(blockedId));
        booleanBuilder.and(qUserBlock.isActive.eq(true));

        queryFactory
                .update(qUserBlock)
                .set(qUserBlock.isActive, false)
                .where(booleanBuilder)
                .execute();
    }
}
