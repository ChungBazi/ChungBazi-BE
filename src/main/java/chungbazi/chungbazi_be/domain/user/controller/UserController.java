package chungbazi.chungbazi_be.domain.user.controller;

//import chungbazi.chungbazi_be.domain.auth.AuthTokensGenerator;
import chungbazi.chungbazi_be.domain.user.dto.UserRequestDTO;
import chungbazi.chungbazi_be.domain.user.dto.UserResponseDTO;
import chungbazi.chungbazi_be.domain.user.repository.UserRepository;
import chungbazi.chungbazi_be.domain.user.service.UserService;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository userRepository;
    //private final AuthTokensGenerator authTokensGenerator;
    private final UserService userService;

    @GetMapping("/profile")
    @Operation(summary = "프로필 조회 API", description = "마이페이지 프로필 조회")
    public ApiResponse<UserResponseDTO.ProfileDto> getProfile(){
        return ApiResponse.onSuccess(userService.getProfile());
    }

    @PostMapping("/education")
    public ResponseEntity<ApiResponse<?>> updateEducation(
            @RequestBody UserRequestDTO.EducationDto requestDto,
            @RequestHeader("Authorization") String accessToken
    ) {
        String token = accessToken.replace("Bearer ", "");
        //Long memberId = authTokensGenerator.extractMemberId(token);

        //Education updatedEducation = memberService.updateEducationForCurrentUser(memberId, requestDto);

        Map<String, Object> result = new HashMap<>();
        //result.put("updatedEducation", updatedEducation);

        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

    @PostMapping("/employment")
    public ResponseEntity<ApiResponse<?>> updateEmployment(
            @RequestBody UserRequestDTO.EmploymentDto requestDto,
            @RequestHeader("Authorization") String accessToken
    ) {
        String token = accessToken.replace("Bearer ", "");
        //Long memberId = authTokensGenerator.extractMemberId(token);

        //Employment updatedEmployment = memberService.updateEmploymentForCurrentUser(memberId, requestDto);

        Map<String, Object> result = new HashMap<>();
        //result.put("updatedEmployment", updatedEmployment);

        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

    @PostMapping("/income")
    public ResponseEntity<ApiResponse<?>> updateIncome(
            @RequestBody UserRequestDTO.IncomeDto requestDto,
            @RequestHeader("Authorization") String accessToken
    ) {
        String token = accessToken.replace("Bearer ", "");
        //Long memberId = authTokensGenerator.extractMemberId(token);

        //Income updatedIncome = memberService.updateIncomeForCurrentUser(memberId, requestDto);

        Map<String, Object> result = new HashMap<>();
        //result.put("updatedIncome", updatedIncome);

        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

    @PostMapping("/region")
    public ResponseEntity<ApiResponse<?>> updateRegion(
            @RequestBody UserRequestDTO.RegionDto requestDto,
            @RequestHeader("Authorization") String accessToken
    ) {
        String token = accessToken.replace("Bearer ", "");
        //Long memberId = authTokensGenerator.extractMemberId(token);

        //Region updatedRegion = memberService.updateRegionForCurrentUser(memberId, requestDto);

        Map<String, Object> result = new HashMap<>();
        //result.put("updatedRegion", updatedRegion);

        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

    @PostMapping("/interest")
    public ResponseEntity<ApiResponse<?>> updateInterest(
            @RequestBody UserRequestDTO.InterestDto requestDto,
            @RequestHeader("Authorization") String accessToken
    ) {
        String token = accessToken.replace("Bearer ", "");
        //Long memberId = authTokensGenerator.extractMemberId(token);

        //List<String> updatedInterests = memberService.updateInterestForCurrentUser(memberId, requestDto);

        Map<String, Object> result = new HashMap<>();
        //result.put("updatedInterests", updatedInterests);

        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

    @PostMapping("/addition")
    public ResponseEntity<ApiResponse<?>> updateAddition(
            @RequestBody UserRequestDTO.AdditionDto requestDto,
            @RequestHeader("Authorization") String accessToken
    ) {
        String token = accessToken.replace("Bearer ", "");
        //Long memberId = authTokensGenerator.extractMemberId(token);

        //List<String> updatedAdditions = memberService.updateAdditionForCurrentUser(memberId, requestDto);

        Map<String, Object> result = new HashMap<>();
        //result.put("updatedAdditions", updatedAdditions);

        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }
}