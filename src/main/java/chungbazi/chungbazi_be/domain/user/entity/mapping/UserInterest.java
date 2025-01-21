package chungbazi.chungbazi_be.domain.user.entity.mapping;

import chungbazi.chungbazi_be.domain.user.entity.Interest;
import chungbazi.chungbazi_be.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_id",nullable = false)
    private Interest interest;

}
