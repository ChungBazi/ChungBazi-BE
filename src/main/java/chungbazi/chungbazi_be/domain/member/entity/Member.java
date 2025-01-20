package chungbazi.chungbazi_be.domain.member.entity;

import chungbazi.chungbazi_be.domain.member.entity.enums.*;
import chungbazi.chungbazi_be.domain.member.entity.mapping.MemberAddition;
import chungbazi.chungbazi_be.domain.member.entity.mapping.MemberInterest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Builder
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String birthYear;
    private String birthDay;

    @Enumerated(EnumType.STRING)
    private OAuthProvider oAuthProvider;

    @Enumerated(EnumType.STRING)
    private Education education;

    @Enumerated(EnumType.STRING)
    private Employment employment;

    @Enumerated(EnumType.STRING)
    private Income income;

    @Enumerated(EnumType.STRING)
    private Region region;


    @ColumnDefault("0")
    private Integer reward;

    @Column(nullable = false)
    private boolean isDeleted;

    private String imageUrl;

    @OneToMany(mappedBy = "member", cascade = {CascadeType.ALL})
    private List<MemberAddition> memberAdditionList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.ALL})
    private List<MemberInterest> memberInterestList = new ArrayList<>();


    public void updateEducation(Education education) {
        this.education = education;
    }
    public void updateEmployment(Employment employment) {
        this.employment = employment;
    }
    public void updateIncome(Income income) {
        this.income = income;
    }
    public void updateRegion(Region region) {
        this.region = region;
    }
}




