package chungbazi.chungbazi_be.domain.cart.dto;

import java.util.List;
import lombok.Getter;

public class CartRequestDTO {

    @Getter
    public static class CartDeleteList {
        private List<Long> deleteList;
    }

    @Getter
    public static class CalendarDocument {
        private String content;
    }
}
