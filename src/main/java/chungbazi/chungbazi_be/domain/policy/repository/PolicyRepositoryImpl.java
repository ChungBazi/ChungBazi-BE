package chungbazi.chungbazi_be.domain.policy.repository;

import chungbazi.chungbazi_be.domain.policy.entity.Category;
import chungbazi.chungbazi_be.domain.policy.entity.Policy;
import chungbazi.chungbazi_be.domain.policy.entity.QPolicy;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.GeneralException;
import com.querydsl.core.Tuple;
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
public class PolicyRepositoryImpl implements PolicyRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    QPolicy policy = QPolicy.policy;

    @Override
    public List<Tuple> searchPolicyWithName(String keyword, String cursor, int size, String order) {

        // SQL 쿼리 정의
        NumberExpression<Integer> priorityScore = matchNamePriority(keyword);

        List<Tuple> policies = jpaQueryFactory
                .select(policy, priorityScore)
                .from(policy)
                .where(searchName(keyword, policy), ltCursor(cursor, keyword, policy))
                .orderBy(orderSpecifiers(order, policy))
                .limit(size)
                .fetch();

        return policies;

    }

    // 이번에 조회된 마지막 커서 값 반환 (우선순위+id)
    @Override
    public String generateNextCursor(Tuple tuple, String name) {

        Policy policy = tuple.get(QPolicy.policy);
        Integer priority = tuple.get(matchNamePriority(name));

        if (policy == null) {
            throw new GeneralException(ErrorStatus.POLICY_NOT_FOUND);
        }
        Long policyId = policy.getId();

        return priority + "-" + policyId;
    }

    // 카테고리별 정책 조회
    @Override
    public List<Policy> getPolicyWithCategory(Category category, Long cursor, int size, String order) {

        List<Policy> policies = jpaQueryFactory
                .select(policy)
                .from(policy)
                .where(eqCategory(category, policy), ltCursorId(cursor, policy))
                .orderBy(orderSpecifiers(order, policy))
                .limit(size)
                .fetch();

        return policies;
    }


    // 이름 검색
    private BooleanExpression searchName(String name, QPolicy policy) {

        if (name == null) {
            throw new GeneralException(ErrorStatus.NO_SEARCH_NAME);
        }

        return policy.name.contains(name);
    }


    // 일치도 순 정렬, 일치도 같다면 Id 크기로 정렬
    private BooleanExpression ltCursor(String cursor, String keyword, QPolicy policy) {

        if (cursor == null) {
            return null;
        }

        String[] cursorParts = cursor.split("-");
        int cursorMatchScore = Integer.parseInt(cursorParts[0]);
        Long policyId = Long.parseLong(cursorParts[1]);

        // cursor priority보다 우선순위가 낮은 애들
        BooleanExpression matchScoreCondition = matchNamePriority(keyword).lt(cursorMatchScore);
        //cursor priority와 우선순위 같은 애들
        BooleanExpression SameScoreCondition = matchNamePriority(keyword).eq(cursorMatchScore)
                .and(policy.id.lt(policyId));

        // matchScoreCondition 이거나 SameScoreCondition
        return matchScoreCondition.or(SameScoreCondition);
    }

    // 일치도 계산
    private NumberExpression<Integer> matchNamePriority(String keyword) {

        if (keyword == null) {
            throw new GeneralException(ErrorStatus.NO_SEARCH_NAME);
        }
        return new CaseBuilder()
                .when(QPolicy.policy.name.eq(keyword)).then(4)
                .when(QPolicy.policy.name.startsWith(keyword)).then(3)
                .when(QPolicy.policy.name.contains(keyword)).then(2)
                .otherwise(1);
    }

    // 정렬 방법
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

        if (cursor == null) {
            return null;
        }

        return policy.id.lt(cursor);
    }

    private BooleanExpression eqCategory(Category category, QPolicy policy) {

        if (category == null) {
            return null;
        }

        return policy.category.eq(category);
    }
}
