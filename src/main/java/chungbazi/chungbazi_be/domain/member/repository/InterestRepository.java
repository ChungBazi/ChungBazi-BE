package chungbazi.chungbazi_be.domain.member.repository;

import chungbazi.chungbazi_be.domain.member.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {
    Optional<Interest> findByName(String name);
}
