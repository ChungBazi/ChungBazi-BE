package chungbazi.chungbazi_be.domain.policy.repository;

import chungbazi.chungbazi_be.domain.policy.entity.Category;
import chungbazi.chungbazi_be.domain.policy.entity.Policy;
import com.querydsl.core.Tuple;
import java.util.List;

public interface PolicyRepositoryCustom {

    List<Tuple> searchPolicyWithName(String keyword, String cursor, int size, String order);

    String generateNextCursor(Tuple policy, String name);

    List<Policy> getPolicyWithCategory(Category category, Long cursor, int size, String order);

    List<Policy> findByCategory(Category category, Long cursor, int size, String order);

}