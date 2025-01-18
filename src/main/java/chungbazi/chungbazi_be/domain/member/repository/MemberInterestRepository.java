package chungbazi.chungbazi_be.domain.member.repository;

import chungbazi.chungbazi_be.domain.member.entity.Member;
import chungbazi.chungbazi_be.domain.member.entity.mapping.MemberInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberInterestRepository extends JpaRepository<MemberInterest, Long> {
    void deleteByMember(Member member);
}
