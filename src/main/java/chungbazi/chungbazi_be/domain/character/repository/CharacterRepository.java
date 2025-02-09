package chungbazi.chungbazi_be.domain.character.repository;

import chungbazi.chungbazi_be.domain.character.entity.Character;
import chungbazi.chungbazi_be.domain.user.entity.enums.RewardLevel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    List<Character> findByUserId(Long userId);

    Optional<Character> findByUserIdAndRewardLevel(Long userId, RewardLevel rewardLevel);

    Optional<Character> findTopByUserIdAndOpenOrderByRewardLevelDesc(Long userId, boolean open);

}
