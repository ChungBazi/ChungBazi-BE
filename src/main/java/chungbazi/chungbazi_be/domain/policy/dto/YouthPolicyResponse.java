package chungbazi.chungbazi_be.domain.policy.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)   // 명시되지 않은 필드는 매핑 시 무시
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class YouthPolicyResponse {

    @JsonProperty("plcyNo")
    private String plcyNo; // 정책 ID

    @JsonProperty("bscPlanPlcyWayNo")
    private String bscPlanPlcyWayNo; // 카테고리 코드

    @JsonProperty("plcyNm")
    private String plcyNm; // 정책명

    @JsonProperty("plcyExplnCn")
    private String plcyExplnCn; // 정책설명내용(소개)

    @JsonProperty("plcySprtCn")
    private String plcySprtCn; // 지원내용

    // 시작날짜
    private String bizPrdBgngYmd;
    // 종료날짜
    private String bizPrdEndYmd;

    @JsonProperty("bizPrdEtcCn")
    private String bizPrdEtcCn; // 예: "계속"

    private LocalDate startDate;
    private LocalDate endDate;
    /*
        @JsonProperty("ageInfo")
        private String ageInfo; // 연령 정보
    */
    @JsonProperty("sprtTrgtMinAge")
    private String sprtTrgtMinAge;  // 지원대상 최소 연령

    @JsonProperty("sprtTrgtMaxAge")
    private String sprtTrgtMaxAge;  // 지원대상 최대 연령
    /*
        @JacksonXmlProperty(localName = "majrRqisCn")
        private String majrRqisCn; // 전공 요건 내용

        @JacksonXmlProperty(localName = "empmSttsCn")
        private String empmSttsCn; // 취업 상태 내용

    @JacksonXmlProperty(localName = "prcpCn")
    private String prcpCn; // 거주 및 소득 조건 내용
     */
    @JsonProperty("earnCndSeCd")
    private String earnCndSeCd; // 소득 조건 구분 코드 ("0043001":무관, "0043003":earnEtcCn 참고)

    @JsonProperty("earnMinAmt")
    private String earnMinAmt;  // 소득 최소금액

    @JsonProperty("earnMaxAmt")
    private String earnMaxAmt;  // 소득 최대금액

    @JsonProperty("earnEtcCn")
    private String earnEtcCn;  // 소득 기타내용 (0043003이면 참고, 0043001이면 비어있음)

    @JsonProperty("addAplyQlfcCndCn")
    private String addAplyQlfcCndCn;  // 추가신청 자격조건 내용ㅈ
    /*
        @JacksonXmlProperty(localName = "accrRqisCn")
        private String accrRqisCn; // 학력 요건 내용
    */
    @JsonProperty("aplyUrlAddr")
    private String aplyUrlAddr; // 신청 사이트 주소

    @JsonProperty("sbmsnDcmntCn")
    private String sbmsnDcmntCn; // 제출 서류 내용

    @JsonProperty("srngMthdCn")
    private String srngMthdCn; // 심사 발표 내용

    @JsonProperty("plcyAplyMthdCn")
    private String plcyAplyMthdCn; // 정책 신청 방법 내용

    @JsonProperty("refUrlAddr1")
    private String refUrlAddr1; // 참고 사이트 URL1

    @JsonProperty("refUrlAddr2")
    private String refUrlAddr2; // 참고 사이트 URL2


    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    @JsonSetter("bizPrdBgngYmd")
    public void setBizPrdBgngYmd(String bizPrdBgngYmd) {
        LocalDate parsedStart = parseDate(bizPrdBgngYmd);
        if (parsedStart != null) {
            this.startDate = parsedStart;
        }
    }

    @JsonSetter("bizPrdEndYmd")
    public void setBizPrdEndYmd(String bizPrdEndYmd) {
        LocalDate parsedEnd = parseDate(bizPrdEndYmd);
        if (parsedEnd != null) {
            this.endDate = parsedEnd;
        }
    }

    private LocalDate parseDate(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        if (trimmed.isEmpty() || value.trim().equals("        ")) {
            return null;
        }

        try {
             return LocalDate.parse(trimmed, FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }


/*
    @JsonSetter("rqutPrdCn") // Jackson이 XML에서 rqutPrdCn 태그를 찾고 setRqutPrdCn 메서드를 호출하여 startDate, endDate 설정
    public void setRqutPrdCn(String rqutPrdCn) {
        this.rqutPrdCn = rqutPrdCn; // XML 데이터 매핑
        parseDatesFromRqutPrdCn(rqutPrdCn); // 매핑 중에 날짜 파싱
    }

    private void parseDatesFromRqutPrdCn(String rqutPrdCn) {
        if (rqutPrdCn == null || rqutPrdCn.isEmpty()) {
            return;
        }
        String[] lines = rqutPrdCn.split("\n");
        if (lines.length > 0) {
            String firstLine = lines[0].trim();
            if (firstLine.matches("\\d{4}-\\d{2}-\\d{2}~\\d{4}-\\d{2}-\\d{2}")) {
                String[] dateSplit = firstLine.split("~");

                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    this.startDate = LocalDate.parse(dateSplit[0].trim(), formatter);
                    this.endDate = LocalDate.parse(dateSplit[1].trim(), formatter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
 */
}