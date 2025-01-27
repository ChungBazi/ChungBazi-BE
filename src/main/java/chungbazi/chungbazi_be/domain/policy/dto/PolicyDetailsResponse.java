package chungbazi.chungbazi_be.domain.policy.dto;

import chungbazi.chungbazi_be.domain.policy.entity.Policy;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PolicyDetailsResponse {

    private String posterUrl;

    private String categoryName;

    private String name;

    private String intro;
    private String content;

    private LocalDate startDate;
    private LocalDate endDate;

    private String minAge;
    private String maxAge;
    private String minIncome;
    private String maxIncome;
    private String incomeEtc;
    private String additionCondition;

    private String applyProcedure;

    private String document;

    private String result;

    private String referenceUrl1;
    private String referenceUrl2;

    private String registerUrl;


    public static PolicyDetailsResponse from(Policy policy) {
        return PolicyDetailsResponse.builder()
                .posterUrl(policy.getPosterUrl())
                .categoryName(policy.getCategory().getKoreanName())
                .name(policy.getName())
                .intro(policy.getIntro())
                .content(policy.getContent())
                .startDate(policy.getStartDate())
                .endDate(policy.getEndDate())
                .minAge(policy.getMinAge())
                .maxAge(policy.getMaxAge())
                .minIncome(policy.getMinIncome())
                .maxIncome(policy.getMaxIncome())
                .incomeEtc(policy.getIncomeEtc())
                .additionCondition(policy.getAdditionCondition())
                .applyProcedure(policy.getApplyProcedure())
                .document(policy.getDocument())
                .result(policy.getResult())
                .referenceUrl1(policy.getReferenceUrl1())
                .referenceUrl2(policy.getReferenceUrl2())
                .registerUrl(policy.getRegisterUrl())
                .build();
    }
}
