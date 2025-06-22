package chungbazi.chungbazi_be.domain.user.repository.UserBlockRepository;

import chungbazi.chungbazi_be.domain.user.entity.UserBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBlockRepository extends JpaRepository<UserBlock, Long>,UserBlockRepositoryCustom {
    Optional<UserBlock> findByBlockerIdAndBlockedIdAndIsActiveTrue(Long blockerId, Long blockedId);
}
