package chungbazi.chungbazi_be.domain.cart.converter;

import chungbazi.chungbazi_be.domain.cart.dto.CartResponseDTO;
import chungbazi.chungbazi_be.domain.cart.dto.CartResponseDTO.CartPolicy;
import chungbazi.chungbazi_be.domain.policy.entity.Category;
import chungbazi.chungbazi_be.domain.policy.entity.Policy;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CartConverter {

    public static CartResponseDTO.CartPolicy toCartPolicy(Policy policy) {
        int dDay = (int) ChronoUnit.DAYS.between(policy.getEndDate(), LocalDate.now());
        return CartResponseDTO.CartPolicy.builder()
                .name(policy.getName())
                .startDate(policy.getStartDate())
                .endDate(policy.getEndDate())
                .dDay(dDay)
                .build();
    }

    public static CartResponseDTO.CartPolicyList toCartPolicyList(Category category, List<CartPolicy> policyDetails) {
        return CartResponseDTO.CartPolicyList.builder()
                .categoryName(category.getKoreanName())
                .cartPolicies(policyDetails)
                .build();
    }
}
