package chungbazi.chungbazi_be.domain.character.repository;

import chungbazi.chungbazi_be.domain.character.entity.Character;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    List<Character> findByUserId(Long userId);
}
