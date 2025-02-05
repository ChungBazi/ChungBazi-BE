package chungbazi.chungbazi_be.domain.cart.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

public class CartResponseDTO {

    //장바구니 정책 리스트
    @Getter
    @Builder
    public static class CartPolicyList {
        private String categoryName;
        private List<CartPolicy> cartPolicies;
    }

    //장바구니 개별 정책
    @Getter
    @Builder
    public static class CartPolicy {
        private String name;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
        private LocalDate startDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
        private LocalDate endDate;
        private int dDay;
    }
}
