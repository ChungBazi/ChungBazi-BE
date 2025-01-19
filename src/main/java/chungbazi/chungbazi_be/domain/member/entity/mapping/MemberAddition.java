package chungbazi.chungbazi_be.domain.member.entity.mapping;

import chungbazi.chungbazi_be.domain.member.entity.Member;
import chungbazi.chungbazi_be.domain.member.entity.Addition;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberAddition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addition_id",nullable = false)
    private Addition addition;
}
