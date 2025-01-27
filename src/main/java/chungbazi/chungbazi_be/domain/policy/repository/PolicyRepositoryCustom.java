package chungbazi.chungbazi_be.domain.policy.repository;

import com.querydsl.core.Tuple;
import java.util.List;

public interface PolicyRepositoryCustom {

    List<Tuple> searchPolicyWithName(String keyword, String cursor, int size, String order);

    String generateNextCursor(Tuple policy, String name);
}
