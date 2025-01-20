package chungbazi.chungbazi_be.domain.user.repository;

import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.entity.mapping.UserAddition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAdditionRepository extends JpaRepository<UserAddition, Long> {
    void deleteByUser(User user);
}

