package chungbazi.chungbazi_be.domain.member.controller;

import chungbazi.chungbazi_be.domain.auth.AuthTokensGenerator;
import chungbazi.chungbazi_be.domain.member.dto.MemberRequestDTO;
import chungbazi.chungbazi_be.domain.member.service.MemberService;
import chungbazi.chungbazi_be.domain.member.repository.MemberRepository;
import chungbazi.chungbazi_be.domain.member.entity.Member;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class MemberController {
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<Member>> findAll() {
        return ResponseEntity.ok(memberRepository.findAll());
    }

    @GetMapping("/{accessToken}")
    public ResponseEntity<Member> findByAccessToken(@PathVariable String accessToken) {
        Long memberId = authTokensGenerator.extractMemberId(accessToken);
        return ResponseEntity.ok(memberRepository.findById(memberId).get());
    }

    @PostMapping("/education")
    public ResponseEntity<ApiResponse<?>> updateEducation(
            @RequestBody MemberRequestDTO.EducationDto requestDto,
            @RequestHeader("Authorization") String accessToken
    ) {
        String token = accessToken.replace("Bearer ", "");
        Long memberId = authTokensGenerator.extractMemberId(token);
        String message = memberService.updateEducationForCurrentUser(memberId, requestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(message));
    }

    @PostMapping("/employment")
    public ResponseEntity<ApiResponse<?>> updateEmployment(
            @RequestBody MemberRequestDTO.EmploymentDto requestDto,
            @RequestHeader("Authorization") String accessToken
    ) {
        String token = accessToken.replace("Bearer ", "");
        Long memberId = authTokensGenerator.extractMemberId(token);
        String message = memberService.updateEmploymentForCurrentUser(memberId, requestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(message));
    }

    @PostMapping("/income")
    public ResponseEntity<ApiResponse<?>> updateIncome(
            @RequestBody MemberRequestDTO.IncomeDto requestDto,
            @RequestHeader("Authorization") String accessToken
    ) {
        String token = accessToken.replace("Bearer ", "");
        Long memberId = authTokensGenerator.extractMemberId(token);
        String message = memberService.updateIncomeForCurrentUser(memberId, requestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(message));
    }

    @PostMapping("/region")
    public ResponseEntity<ApiResponse<?>> updateRegion(
            @RequestBody MemberRequestDTO.RegionDto requestDto,
            @RequestHeader("Authorization") String accessToken
    ) {
        String token = accessToken.replace("Bearer ", "");
        Long memberId = authTokensGenerator.extractMemberId(token);
        String message = memberService.updateRegionForCurrentUser(memberId, requestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(message));
    }

    @PostMapping("/interest")
    public ResponseEntity<ApiResponse<?>> updateInterest(
            @RequestBody MemberRequestDTO.InterestDto requestDto,
            @RequestHeader("Authorization") String accessToken
    ) {
        String token = accessToken.replace("Bearer ", "");
        Long memberId = authTokensGenerator.extractMemberId(token);
        String message = memberService.updateInterestForCurrentUser(memberId, requestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(message));
    }

    @PostMapping("/addition")
    public ResponseEntity<ApiResponse<?>> updateAddition(
            @RequestBody MemberRequestDTO.AdditionDto requestDto,
            @RequestHeader("Authorization") String accessToken
    ) {
        String token = accessToken.replace("Bearer ", "");
        Long memberId = authTokensGenerator.extractMemberId(token);
        String message = memberService.updateAdditionForCurrentUser(memberId, requestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(message));
    }
}