package chungbazi.chungbazi_be.domain.policy.repository;

import chungbazi.chungbazi_be.domain.policy.entity.Category;
import chungbazi.chungbazi_be.domain.policy.entity.Policy;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy, Long>, PolicyRepositoryCustom {

    boolean existsByBizId(String bizId);

    //챗봇 정책찾기용 -> 추후 수정 예정
    List<Policy> findTop5ByCategoryOrderByCreatedAtDesc(Category category);
}