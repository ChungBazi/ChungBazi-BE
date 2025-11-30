package chungbazi.chungbazi_be.domain.user.entity.enums;

import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Region {
    GANGNAM("서울시 강남구"),
    GANGDONG("서울시 강동구"),
    GANGBUK("서울시 강북구"),
    GANGSEO("서울시 강서구"),
    GWANAK("서울시 관악구"),
    GWANGJIN("서울시 광진구"),
    GURO("서울시 구로구"),
    GEUMCHEON("서울시 금천구"),
    NOWON("서울시 노원구"),
    DOBONG("서울시 도봉구"),
    DONGDAEMUN("서울시 동대문구"),
    DONGJAK("서울시 동작구"),
    MAPO("서울시 마포구"),
    SEODAEMUN("서울시 서대문구"),
    SEOCHO("서울시 서초구"),
    SEONGDONG("서울시 성동구"),
    SEONGBUK("서울시 성북구"),
    SONGPA("서울시 송파구"),
    YANGCHEON("서울시 양천구"),
    YEONGDEUNGPO("서울시 영등포구"),
    YONGSAN("서울시 용산구"),
    EUNPYEONG("서울시 은평구"),
    JONGNO("서울시 종로구"),
    JUNG("서울시 중구"),
    JUNGNANG("서울시 중랑구");

    private final String description;

    Region(String description) {
        this.description = description;
    }

    @JsonValue
    public String toJson() {
        return description; // 한글 값 반환
    }

    @JsonCreator
    public static Region fromJson(String value) {
        return Arrays.stream(Region.values())
                .filter(e -> e.description.equals(value))
                .findFirst()
                .orElseThrow(() -> new BadRequestHandler(ErrorStatus.INVALID_VALUE));
    }
}
