package chungbazi.chungbazi_be.domain.member.converter;

import chungbazi.chungbazi_be.domain.member.dto.MemberResponseDTO;
import chungbazi.chungbazi_be.domain.member.entity.Member;

public class MemberConverter {
    public static MemberResponseDTO.ProfileDto toProfileDto(Member member) {
        return MemberResponseDTO.ProfileDto.builder()
                .userId(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }
}
