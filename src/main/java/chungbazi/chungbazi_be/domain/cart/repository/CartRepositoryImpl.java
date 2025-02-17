package chungbazi.chungbazi_be.domain.cart.repository;

import chungbazi.chungbazi_be.domain.cart.entity.QCart;
import chungbazi.chungbazi_be.domain.policy.entity.Category;
import chungbazi.chungbazi_be.domain.policy.entity.Policy;
import chungbazi.chungbazi_be.domain.policy.entity.QPolicy;
import chungbazi.chungbazi_be.domain.user.entity.User;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    QCart cart = QCart.cart;
    QPolicy policy = QPolicy.policy;

    @Override
    public List<Policy> findByCategoryAndUser(Category category, Long cursor, User user, int size, String order) {
        return jpaQueryFactory
                .select(policy)
                .from(cart)
                .join(cart.policy, policy) // Cart에서 Policy를 조인
                .where(
                        cart.user.eq(user), // 유저 필터링
                        policy.category.eq(category), // 정책 카테고리 필터링
                        ltCursorId(cursor, policy)
                )
                .orderBy(orderSpecifiers(order, policy))
                .limit(size)
                .fetch();
    }

    private OrderSpecifier<?>[] orderSpecifiers(String order, QPolicy policy) {

        LocalDate today = LocalDate.now();

        // 마감순
        if ("deadline".equals(order)) {
            // 마감 안 지난 항목 -> 마감 지난 항목
            NumberExpression<Integer> priority = new CaseBuilder()
                    .when(policy.endDate.goe(today)).then(0) // 마감 안 지난 항목
                    .otherwise(1); // 마감 지난 항목

            // 정렬 조건 배열로 반환
            return new OrderSpecifier[]{
                    new OrderSpecifier<>(Order.ASC, priority), // 1.우선순위 정렬
                    new OrderSpecifier<>(Order.ASC, policy.endDate) // 2.가까운 날짜 순
            };

        } else { // 최신순, 디폴트
            return new OrderSpecifier[]{
                    new OrderSpecifier<>(Order.DESC, policy.startDate)
            };
        }
    }

    private BooleanExpression ltCursorId(Long cursor, QPolicy policy) {

        if (cursor == null || cursor == 0L) {
            return null;
        }

        return policy.id.lt(cursor);
    }
}
