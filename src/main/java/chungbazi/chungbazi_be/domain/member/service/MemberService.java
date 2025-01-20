package chungbazi.chungbazi_be.domain.member.service;


import chungbazi.chungbazi_be.domain.member.MemberHandler;
import chungbazi.chungbazi_be.domain.member.converter.MemberConverter;
import chungbazi.chungbazi_be.domain.member.dto.MemberRequestDTO;
import chungbazi.chungbazi_be.domain.member.dto.MemberResponseDTO;
import chungbazi.chungbazi_be.domain.member.entity.Addition;
import chungbazi.chungbazi_be.domain.member.entity.Interest;
import chungbazi.chungbazi_be.domain.member.entity.Member;
import chungbazi.chungbazi_be.domain.member.entity.enums.Education;
import chungbazi.chungbazi_be.domain.member.entity.enums.Employment;
import chungbazi.chungbazi_be.domain.member.entity.enums.Income;
import chungbazi.chungbazi_be.domain.member.entity.enums.Region;
import chungbazi.chungbazi_be.domain.member.entity.mapping.MemberAddition;
import chungbazi.chungbazi_be.domain.member.entity.mapping.MemberInterest;
import chungbazi.chungbazi_be.domain.member.repository.*;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AdditionRepository additionRepository;
    private final MemberAdditionRepository memberAdditionRepository;
    private final InterestRepository interestRepository;
    private final MemberInterestRepository memberInterestRepository;

    public MemberResponseDTO.ProfileDto getProfile() {
        // 추후 시큐리티로 id 가져오기 (하드 코딩된 상태)
        Long userId = 1L;

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        return MemberConverter.toProfileDto(member);
    }


    public Education updateEducationForCurrentUser(Long memberId, MemberRequestDTO.EducationDto requestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        member.updateEducation(requestDto.getEducation());
        return member.getEducation();
    }

    public Employment updateEmploymentForCurrentUser(Long memberId, MemberRequestDTO.EmploymentDto requestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        member.updateEmployment(requestDto.getEmployment());
        return member.getEmployment();
    }

    public Income updateIncomeForCurrentUser(Long memberId, MemberRequestDTO.IncomeDto requestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        member.updateIncome(requestDto.getIncome());
        return member.getIncome();
    }

    public Region updateRegionForCurrentUser(Long memberId, MemberRequestDTO.RegionDto requestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        member.updateRegion(requestDto.getRegion());
        return member.getRegion();
    }


    public List<String> updateInterestForCurrentUser(Long memberId, MemberRequestDTO.InterestDto requestDto) {
        List<String> interests = requestDto.getInterests();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        // 기존 관심사 삭제
        memberInterestRepository.deleteByMember(member);

        // 새로운 관심사 저장
        for (String interestName : interests) {
            Interest interest = interestRepository.findByName(interestName)
                    .orElseGet(() -> interestRepository.save(Interest.from(interestName)));

            MemberInterest memberInterest = MemberInterest.builder()
                    .member(member)
                    .interest(interest)
                    .build();
            memberInterestRepository.save(memberInterest);
        }

        return interests;
    }


    public List<String> updateAdditionForCurrentUser(Long memberId, MemberRequestDTO.AdditionDto requestDto) {
        List<String> additionalInfo = requestDto.getAdditionInfo();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        // 기존 추가 정보 삭제
        memberAdditionRepository.deleteByMember(member);

        // 새로운 추가 정보 저장
        for (String additionName : additionalInfo) {
            Addition addition = additionRepository.findByName(additionName)
                    .orElseGet(() -> additionRepository.save(Addition.from(additionName)));

            MemberAddition memberAddition = MemberAddition.builder()
                    .member(member)
                    .addition(addition)
                    .build();
            memberAdditionRepository.save(memberAddition);
        }

        return additionalInfo;
    }
}
