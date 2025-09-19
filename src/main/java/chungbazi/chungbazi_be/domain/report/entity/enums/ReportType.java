package chungbazi.chungbazi_be.domain.report.entity.enums;

public enum ReportType {
    POST("게시글"),
    USER("사용자"),
    COMMENT("댓글");

    private final String display;

    ReportType(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }

}
