package chungbazi.chungbazi_be.domain.user.repository;

import chungbazi.chungbazi_be.domain.community.entity.Post;
import chungbazi.chungbazi_be.domain.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    boolean existsByName(String name);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.name = '(알 수 없음)', u.email = CONCAT('deleted_', u.id, '@chungbazi.com') WHERE u.id = :userId")
    void anonymizeUser(@Param("userId") Long userId);

}