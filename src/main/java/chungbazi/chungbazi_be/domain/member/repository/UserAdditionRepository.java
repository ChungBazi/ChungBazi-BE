package chungbazi.chungbazi_be.domain.member.repository;

import chungbazi.chungbazi_be.domain.member.entity.User;
import chungbazi.chungbazi_be.domain.member.entity.mapping.UserAddition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAdditionRepository extends JpaRepository<UserAddition, Long> {
    void deleteByMember(User user);
}

