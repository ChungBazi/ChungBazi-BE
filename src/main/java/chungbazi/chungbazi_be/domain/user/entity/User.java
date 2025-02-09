package chungbazi.chungbazi_be.domain.user.entity;

import chungbazi.chungbazi_be.domain.character.entity.Character;
import chungbazi.chungbazi_be.domain.community.entity.Comment;
import chungbazi.chungbazi_be.domain.community.entity.Post;
import chungbazi.chungbazi_be.domain.notification.entity.Notification;
import chungbazi.chungbazi_be.domain.notification.entity.NotificationSetting;
import chungbazi.chungbazi_be.domain.user.entity.enums.*;
import chungbazi.chungbazi_be.domain.user.entity.mapping.UserAddition;
import chungbazi.chungbazi_be.domain.user.entity.mapping.UserInterest;
import chungbazi.chungbazi_be.global.entity.Uuid;
import jakarta.persistence.*;
import java.util.Arrays;
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

    @Column(nullable = false)
    private boolean isDeleted;

    @Setter
    @ColumnDefault("false")
    private boolean surveyStatus;

    // 캐릭터 관련
    @Enumerated(EnumType.STRING)
    @Setter
    @Builder.Default
    private RewardLevel characterImg = RewardLevel.LEVEL_1;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Character> characters;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private RewardLevel reward = RewardLevel.LEVEL_1;

    // 삭제 예정
    @OneToOne
    @JoinColumn(name = "uuid_id")
    private Uuid uuid;

    // 커뮤니티 관련
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // 유저 정보 관련
    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<UserAddition> userAdditionList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<UserInterest> userInterestList = new ArrayList<>();

    // 알람 관련
    @OneToMany(mappedBy = "user",cascade = {CascadeType.ALL})
    private List<Notification> notificationList = new ArrayList<>();

    @OneToOne(mappedBy = "user",cascade = {CascadeType.ALL})
    private NotificationSetting notificationSetting;


    // 유저 정보 관련
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
    public void updateRewardLevel(RewardLevel reward) {this.reward = reward;}

    // 알람 관련
    public void updateNotificationSetting(NotificationSetting notificationSetting) {this.notificationSetting = notificationSetting;}

    @PostPersist
    public void postPersistInitialization() {
        // 알람 초기화
        if (this.notificationSetting == null) {
            this.notificationSetting = new NotificationSetting(this);
        }
        // 캐릭터 리스트 초기화
        if (characters == null || characters.isEmpty()) {
            this.characters = Arrays.stream(RewardLevel.values())
                    .map(level -> Character.builder()
                            .user(this)
                            .rewardLevel(level)
                            .isOpen(level == RewardLevel.LEVEL_1)
                            .build())
                    .toList();
        }
    }

}




