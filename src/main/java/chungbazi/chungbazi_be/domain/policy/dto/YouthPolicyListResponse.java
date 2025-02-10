package chungbazi.chungbazi_be.domain.policy.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class YouthPolicyListResponse {

    @JsonProperty("result")
    @JsonIgnoreProperties(ignoreUnknown = true)   // 명시되지 않은 필드는 매핑 시 무시
    private Result result;


    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)   // 명시되지 않은 필드는 매핑 시 무시
    public static class Result {
        @JsonProperty("youthPolicyList")
        private List<YouthPolicyResponse> youthPolicyList;
    }

/*
    // Jackson이 List를 올바르게 매핑하기 위해 (Jackson에게 XML의 배열 요소가 별도의 래퍼 태그 없이 반복된다고 알려줌)
    @JacksonXmlElementWrapper(localName = "youthPolicy", useWrapping = false)
    @JacksonXmlProperty(localName = "youthPolicy")
    private List<YouthPolicyResponse> youthPolicyList; // youthPolicy 항목들
*/

}