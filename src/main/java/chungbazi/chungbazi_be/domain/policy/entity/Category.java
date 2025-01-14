package chungbazi.chungbazi_be.domain.policy.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Category {
    JOBS("일자리","023010"),
    HOUSING("주거", "023020"),
    EDUCATION("023030", "교육"),
    WELFARE_CULTURE("023040", "복지.문화"),
    PARTICIPATION_RIGHTS("023050", "참여.권리");

    private final String code;
    private final String koreanName;
}
