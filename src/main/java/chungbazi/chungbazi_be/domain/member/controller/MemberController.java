package chungbazi.chungbazi_be.domain.member.controller;

import chungbazi.chungbazi_be.domain.auth.AuthTokensGenerator;
import chungbazi.chungbazi_be.domain.member.dto.MemberRequestDTO;
import chungbazi.chungbazi_be.domain.member.entity.enums.Education;
import chungbazi.chungbazi_be.domain.member.entity.enums.Employment;
import chungbazi.chungbazi_be.domain.member.entity.enums.Income;
import chungbazi.chungbazi_be.domain.member.entity.enums.Region;
import chungbazi.chungbazi_be.domain.member.repository.MemberRepository;
import chungbazi.chungbazi_be.domain.member.service.MemberService;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class MemberController {
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final MemberService memberService;

    @PostMapping("/education")
    public ResponseEntity<ApiResponse<?>> updateEducation(
            @RequestBody MemberRequestDTO.EducationDto requestDto,
            @RequestHeader("Authorization") String accessToken
    ) {
        String token = accessToken.replace("Bearer ", "");
        Long memberId = authTokensGenerator.extractMemberId(token);

        Education updatedEducation = memberService.updateEducationForCurrentUser(memberId, requestDto);

        Map<String, Object> result = new HashMap<>();
        result.put("updatedEducation", updatedEducation);

        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

    @PostMapping("/employment")
    public ResponseEntity<ApiResponse<?>> updateEmployment(
            @RequestBody MemberRequestDTO.EmploymentDto requestDto,
            @RequestHeader("Authorization") String accessToken
    ) {
        String token = accessToken.replace("Bearer ", "");
        Long memberId = authTokensGenerator.extractMemberId(token);

        Employment updatedEmployment = memberService.updateEmploymentForCurrentUser(memberId, requestDto);

        Map<String, Object> result = new HashMap<>();
        result.put("updatedEmployment", updatedEmployment);

        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

    @PostMapping("/income")
    public ResponseEntity<ApiResponse<?>> updateIncome(
            @RequestBody MemberRequestDTO.IncomeDto requestDto,
            @RequestHeader("Authorization") String accessToken
    ) {
        String token = accessToken.replace("Bearer ", "");
        Long memberId = authTokensGenerator.extractMemberId(token);

        Income updatedIncome = memberService.updateIncomeForCurrentUser(memberId, requestDto);

        Map<String, Object> result = new HashMap<>();
        result.put("updatedIncome", updatedIncome);

        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

    @PostMapping("/region")
    public ResponseEntity<ApiResponse<?>> updateRegion(
            @RequestBody MemberRequestDTO.RegionDto requestDto,
            @RequestHeader("Authorization") String accessToken
    ) {
        String token = accessToken.replace("Bearer ", "");
        Long memberId = authTokensGenerator.extractMemberId(token);

        Region updatedRegion = memberService.updateRegionForCurrentUser(memberId, requestDto);

        Map<String, Object> result = new HashMap<>();
        result.put("updatedRegion", updatedRegion);

        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

    @PostMapping("/interest")
    public ResponseEntity<ApiResponse<?>> updateInterest(
            @RequestBody MemberRequestDTO.InterestDto requestDto,
            @RequestHeader("Authorization") String accessToken
    ) {
        String token = accessToken.replace("Bearer ", "");
        Long memberId = authTokensGenerator.extractMemberId(token);

        List<String> updatedInterests = memberService.updateInterestForCurrentUser(memberId, requestDto);

        Map<String, Object> result = new HashMap<>();
        result.put("updatedInterests", updatedInterests);

        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

    @PostMapping("/addition")
    public ResponseEntity<ApiResponse<?>> updateAddition(
            @RequestBody MemberRequestDTO.AdditionDto requestDto,
            @RequestHeader("Authorization") String accessToken
    ) {
        String token = accessToken.replace("Bearer ", "");
        Long memberId = authTokensGenerator.extractMemberId(token);

        List<String> updatedAdditions = memberService.updateAdditionForCurrentUser(memberId, requestDto);

        Map<String, Object> result = new HashMap<>();
        result.put("updatedAdditions", updatedAdditions);

        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }
}