package chungbazi.chungbazi_be.domain.report.entity.enums;

public enum ReportReason {
    SPAM("스팸"),
    INAPPROPRIATE_CONTENT("부적절한 내용"),
    HARASSMENT("괴롭힘"),
    HATE_SPEECH("혐오 발언"),
    COPYRIGHT_VIOLATION("저작권 침해"),
    MISINFORMATION("거짓 정보"),
    OTHER("기타");

    private final String description;

    ReportReason(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
