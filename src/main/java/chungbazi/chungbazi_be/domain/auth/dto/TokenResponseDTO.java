package chungbazi.chungbazi_be.domain.auth.dto;

import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponseDTO {
    private Long userId;
    private String userName;
    private Boolean isFirst;


    public static TokenResponseDTO of(Long userId, String userName, Boolean isFirst) {
        return new TokenResponseDTO(userId, userName, isFirst);
    }

}