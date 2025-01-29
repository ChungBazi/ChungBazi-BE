package chungbazi.chungbazi_be.global.repository;

import chungbazi.chungbazi_be.global.entity.Uuid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UuidRepository extends JpaRepository<Uuid, Long> {

}
