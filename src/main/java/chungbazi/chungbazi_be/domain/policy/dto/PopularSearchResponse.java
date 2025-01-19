package chungbazi.chungbazi_be.domain.policy.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PopularSearchResponse {

    List<String> keywords;

    public static PopularSearchResponse from(List<String> result) {
        return new PopularSearchResponse(result);
    }
}
