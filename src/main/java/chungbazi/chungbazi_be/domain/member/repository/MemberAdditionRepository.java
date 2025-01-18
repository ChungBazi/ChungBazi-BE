package chungbazi.chungbazi_be.domain.member.repository;

import chungbazi.chungbazi_be.domain.member.entity.Member;
import chungbazi.chungbazi_be.domain.member.entity.mapping.MemberAddition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAdditionRepository extends JpaRepository<MemberAddition, Long> {
    void deleteByMember(Member member);
}

