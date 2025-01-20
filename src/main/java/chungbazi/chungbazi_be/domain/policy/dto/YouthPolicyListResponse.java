package chungbazi.chungbazi_be.domain.policy.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class YouthPolicyListResponse {

    @JacksonXmlProperty(localName = "pageIndex")
    private int pageIndex;

    @JacksonXmlProperty(localName = "totalCnt")
    private int totalCnt;

    // Jackson이 List를 올바르게 매핑하기 위해 (Jackson에게 XML의 배열 요소가 별도의 래퍼 태그 없이 반복된다고 알려줌)
    @JacksonXmlElementWrapper(localName = "youthPolicy", useWrapping = false)
    @JacksonXmlProperty(localName = "youthPolicy")
    private List<YouthPolicyResponse> youthPolicyList; // youthPolicy 항목들


}
