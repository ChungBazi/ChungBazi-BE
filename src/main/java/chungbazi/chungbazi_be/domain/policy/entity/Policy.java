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
    private Long id;

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

    // 최소 연령
    @Column(length = 1000)
    private String minAge;

    // 최대 연령
    @Column(length = 1000)
    private String maxAge;

    /*
    // 전공 요건 내용
    @Column(length = 1000)
    private String major;

    // 취업 상태 내용
    @Column(length = 1000)
    private String employment;
    */

    // 소득 조건 코드
    private String incomeCode;

    // 소득 최소 금액
    private String minIncome;

    // 소득 최대 금액
    private String maxIncome;

    // 기타내용 (incomeCode가 "0043003"이면 참고, "0043001"이면 비어있음)
    @Column(length = 1000)
    private String incomeEtc;

    private String additionCondition;

    /*
    // 학력 요건 내용
    @Column(length = 1000)
    private String education;
    */

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

    // 정책 신청 방법 내용
    @Column(length = 1000)
    private String applyProcedure;

    // 참고 사이트 URL1
    private String referenceUrl1;

    // 참고 사이트 URL2
    private String referenceUrl2;

    // 포스터 사진 url
    private String posterUrl;


    public static Policy toEntity(YouthPolicyResponse dto) {

        String code = dto.getBscPlanPlcyWayNo();
        Category dtoCategory = Category.fromCode(code);

        return Policy.builder()
                .category(dtoCategory)
                .name(dto.getPlcyNm())
                .intro(dto.getPlcyExplnCn())
                .content(dto.getPlcySprtCn())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .minAge(dto.getSprtTrgtMinAge())
                .maxAge(dto.getSprtTrgtMaxAge())
                .incomeCode(dto.getEarnCndSeCd())
                .minIncome(dto.getEarnMinAmt())
                .maxIncome(dto.getEarnMaxAmt())
                .incomeEtc(dto.getEarnEtcCn())
                .additionCondition(dto.getAddAplyQlfcCndCn())
                .registerUrl(dto.getAplyUrlAddr())
                .bizId(dto.getPlcyNo())
                .document(dto.getSbmsnDcmntCn())
                .result(dto.getSrngMthdCn())
                .applyProcedure(dto.getPlcyAplyMthdCn())
                .referenceUrl1(dto.getRefUrlAddr1())
                .referenceUrl2(dto.getRefUrlAddr2())
                .build();
    }
}


