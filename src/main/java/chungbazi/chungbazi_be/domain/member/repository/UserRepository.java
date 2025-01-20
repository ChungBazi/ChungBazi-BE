package chungbazi.chungbazi_be.domain.member.repository;

import chungbazi.chungbazi_be.domain.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}