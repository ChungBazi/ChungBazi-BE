package chungbazi.chungbazi_be.domain.policy.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class YouthPolicyResponse {

    @JacksonXmlProperty(localName = "bizId")
    private String bizId; // 정책 ID

    @JacksonXmlProperty(localName = "polyRlmCd")
    private String polyRlmCd; // 카테고리 코드

    @JacksonXmlProperty(localName = "polyBizSjnm")
    private String polyBizSjnm; // 정책명

    @JacksonXmlProperty(localName = "polyItcnCn")
    private String polyItcnCn; // 정책소개

    @JacksonXmlProperty(localName = "sporCn")
    private String sporCn; // 지원내용

    /*
    @JacksonXmlProperty(localName = "rqutPrdCn")
    private String rqutPrdCn; // 신청 기간 (텍스트 형태)
*/
    private String rqutPrdCn;

    private LocalDate startDate;
    private LocalDate endDate;

    @JacksonXmlProperty(localName = "ageInfo")
    private String ageInfo; // 연령 정보

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


    /*  public void assignStartDate(LocalDate startDate) {
          this.startDate = startDate;
      }

      public void assignEndDate(LocalDate endDate) {
          this.endDate = endDate;
      }
  */
    @JsonSetter("rqutPrdCn") // Jackson이 XML에서 rqutPrdCn 태그를 찾고 setRqutPrdCn 메서드를 호출하여 값을 설정
    public void setRqutPrdCn(String rqutPrdCn) {
        System.out.println("setter 테스트: " + rqutPrdCn);
        this.rqutPrdCn = rqutPrdCn; // XML 데이터 매핑
        parseDatesFromRqutPrdCn(rqutPrdCn); // 매핑 중에 날짜 파싱
    }

    private void parseDatesFromRqutPrdCn(String rqutPrdCn) {
        System.out.println("프라이빗 테스트: " + rqutPrdCn);
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
                    System.out.println("endDate: " + endDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
