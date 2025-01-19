package chungbazi.chungbazi_be.domain.policy.entity;

import chungbazi.chungbazi_be.domain.policy.dto.YouthPolicyResponse;
import chungbazi.chungbazi_be.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Policy extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_id")
    private Long policyId;

    //카테고리
    @Enumerated(EnumType.STRING)
    @NotNull
    private Category category;

    //정책명
    @NotNull
    private String name;

    //정책소개
    @Column(length = 1000)
    private String intro;

    //지원내용
    @Column(length = 1000)
    private String content;

    // 신청시작 날짜
    private LocalDate startDate;

    // 신청 끝나는 날짜
    private LocalDate endDate;

    // 연령 정보
    @Column(length = 1000)
    private String age;

    // 전공 요건 내용
    @Column(length = 1000)
    private String major;

    // 취업 상태 내용
    @Column(length = 1000)
    private String employment;

    // 거주지 및 소득 조건 내용
    @Column(length = 1000)
    private String residenceIncome;

    // 학력 요건 내용
    @Column(length = 1000)
    private String education;

    // 신청 사이트 주소
    private String registerUrl;

    // Open API 내 정책 ID
    @NotNull
    private String bizId;

    // 제출 서류 내용
    @Column(length = 1000)
    private String document;

    // 심사 발표 내용
    @Column(length = 1000)
    private String result;

    // 신청 절차 내용
    @Column(length = 1000)
    private String applyProcedure;

    // 참고 사이트 URL1
    private String referenceUrl1;

    // 참고 사이트 URL2
    private String referenceUrl2;


    public static Policy toEntity(YouthPolicyResponse dto) {

        String code = dto.getPolyRlmCd();
        Category dtoCategory = Category.fromCode(code);

        String date = dto.getRqutPrdCn();

        LocalDate startDate = dto.getStartDate();
        LocalDate endDate = dto.getEndDate();

        return Policy.builder()
                .category(dtoCategory)
                .name(dto.getPolyBizSjnm())
                .intro(dto.getPolyItcnCn())
                .content(dto.getSporCn())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .age(dto.getAgeInfo())
                .major(dto.getMajrRqisCn())
                .employment(dto.getEmpmSttsCn())
                .residenceIncome(dto.getPrcpCn())
                .education(dto.getAccrRqisCn())
                .registerUrl(dto.getRqutUrla())
                .bizId(dto.getBizId())
                .document(dto.getPstnPaprCn())
                .result(dto.getJdgnPresCn())
                .applyProcedure(dto.getRqutProcCn())
                .referenceUrl1(dto.getRfcSiteUrla1())
                .referenceUrl2(dto.getRfcSiteUrla2())
                .build();
    }
}


