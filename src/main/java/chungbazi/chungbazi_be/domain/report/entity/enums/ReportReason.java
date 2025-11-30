package chungbazi.chungbazi_be.domain.report.entity.enums;

public enum ReportReason {
    DOES_NOT_APPEAL("마음에 들지 않아요"),
    SPAM("스팸이에요"),
    SEXUAL_CONTENT("선정적이에요"),
    HATE_SPEECH("혐오 발언이에요"),
    FALSE_INFORMATION("허위 정보에요"),
    OTHER("기타 (직접 입력)");

    private final String description;

    ReportReason(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
