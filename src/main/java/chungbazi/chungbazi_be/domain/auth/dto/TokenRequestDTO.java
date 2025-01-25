package chungbazi.chungbazi_be.domain.auth.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequestDTO {
    private String name;
    private String email;
    private String nickname;
    private String gender;
    private String imageUrl;
    private String birthYear;
    private String birthDay;
}