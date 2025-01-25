package chungbazi.chungbazi_be.domain.policy.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    private LocalDate startDate;
    private LocalDate endDate;
    /*
        @JsonProperty("ageInfo")
        private String ageInfo; // 연령 정보
    */
    @JsonProperty("sprtTrgtMinAge")
    private String sprtTrgtMinAge;  // 지원대상 최소 연령

    @JsonProperty("ssprtTrgtMaxAge")
    private String sprtTrgtMaxAge;  // 지원대상 최대 연령

    @JacksonXmlProperty(localName = "majrRqisCn")
    private String majrRqisCn; // 전공 요건 내용

    @JacksonXmlProperty(localName = "empmSttsCn")
    private String empmSttsCn; // 취업 상태 내용

    @JacksonXmlProperty(localName = "prcpCn")
    private String prcpCn; // 거주 및 소득 조건 내용

    @JacksonXmlProperty(localName = "accrRqisCn")
    private String accrRqisCn; // 학력 요건 내용

    @JacksonXmlProperty(localName = "rqutUrla")
    private String rqutUrla; // 신청 사이트 주소

    @JacksonXmlProperty(localName = "pstnPaprCn")
    private String pstnPaprCn; // 제출 서류 내용

    @JacksonXmlProperty(localName = "jdgnPresCn")
    private String jdgnPresCn; // 심사 발표 내용

    @JacksonXmlProperty(localName = "rqutProcCn")
    private String rqutProcCn; // 신청 절차 내용

    @JacksonXmlProperty(localName = "rfcSiteUrla1")
    private String rfcSiteUrla1; // 참고 사이트 URL1

    @JacksonXmlProperty(localName = "rfcSiteUrla2")
    private String rfcSiteUrla2; // 참고 사이트 URL2


    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    @JsonSetter("bizPrdBgngYmd") // JSON에서 값이 들어올 때 startDate 세팅
    public void setBizPrdBgngYmd(String bizPrdBgngYmd) {
        this.bizPrdBgngYmd = bizPrdBgngYmd;
        if (bizPrdBgngYmd != null && !bizPrdBgngYmd.trim().isEmpty() && !bizPrdBgngYmd.trim().equals("        ")) {
            this.startDate = LocalDate.parse(bizPrdBgngYmd, FORMATTER);
        }
    }

    @JsonSetter("bizPrdEndYmd") // JSON에서 값이 들어올 때 endDate 세팅
    public void setbizPrdEndYmd(String bizPrdEndYmd) {
        this.bizPrdEndYmd = bizPrdEndYmd;
        if (bizPrdEndYmd != null && !bizPrdEndYmd.trim().isEmpty() && !bizPrdEndYmd.trim().equals("        ")) {
            this.startDate = LocalDate.parse(bizPrdEndYmd, FORMATTER);
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
