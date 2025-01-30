package chungbazi.chungbazi_be.domain.user.entity;

import chungbazi.chungbazi_be.domain.notification.entity.Notification;
import chungbazi.chungbazi_be.domain.user.entity.enums.*;
import chungbazi.chungbazi_be.domain.user.entity.mapping.UserAddition;
import chungbazi.chungbazi_be.domain.user.entity.mapping.UserInterest;
import chungbazi.chungbazi_be.global.entity.Uuid;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

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

    @Setter
    @ColumnDefault("false")
    private boolean surveyStatus;

    @Column
    @Setter
    private String profileImg;

    @OneToOne
    @JoinColumn(name = "uuid_id")
    private Uuid uuid;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<UserAddition> userAdditionList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<UserInterest> userInterestList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = {CascadeType.ALL})
    private List<Notification> notificationList = new ArrayList<>();


    public void updateEducation(Education education) {
        this.education = education;
    }
    public void updateEmployment(Employment employment) {
        this.employment = employment;
    }
    public void updateIncome(Income income) {
        this.income = income;
    }
    public void updateRegion(Region region) { this.region = region;}
    public void updateIsDeleted(Boolean isDeleted){this.isDeleted = isDeleted;}
}




