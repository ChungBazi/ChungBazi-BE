package chungbazi.chungbazi_be.domain.user.controller;

import chungbazi.chungbazi_be.domain.user.dto.UserRequestDTO;
import chungbazi.chungbazi_be.domain.user.dto.UserResponseDTO;
import chungbazi.chungbazi_be.domain.user.entity.enums.Education;
import chungbazi.chungbazi_be.domain.user.entity.enums.Employment;
import chungbazi.chungbazi_be.domain.user.entity.enums.Income;
import chungbazi.chungbazi_be.domain.user.entity.enums.Region;
import chungbazi.chungbazi_be.domain.user.service.UserService;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import chungbazi.chungbazi_be.global.validation.annotation.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    //private final AuthTokensGenerator authTokensGenerator;
    private final UserService userService;

            @GetMapping("/profile")
            @Operation(summary = "프로필 조회 API", description = "마이페이지 프로필 조회")
            public ApiResponse<UserResponseDTO.ProfileDto> getProfile(@AuthUser Long userId) {
                UserResponseDTO.ProfileDto profile = userService.getProfile(userId);

                return ApiResponse.onSuccess(profile);
            }

            @PostMapping("/education")
            @Operation(summary = "학력 등록 API", description = "유저 학력 등록")
            public ApiResponse<Map<String, Object>> updateEducation(
                    @RequestBody UserRequestDTO.EducationDto requestDto,
                    @AuthUser Long userId
            ) {
                Education updatedEducation = userService.updateEducation(userId, requestDto);

                Map<String, Object> result = new HashMap<>();
                result.put("updatedEducation", updatedEducation);

                return ApiResponse.onSuccess(result);
            }

            @PostMapping("/employment")
            @Operation(summary = "취업상태 등록 API", description = "유저 취업상태 등록")
            public ApiResponse<Map<String, Object>> updateEmployment(
                    @RequestBody UserRequestDTO.EmploymentDto requestDto,
                    @AuthUser Long userId
            ) {
                Employment updatedEmployment = userService.updateEmployment(userId, requestDto);

                Map<String, Object> result = new HashMap<>();
                result.put("updatedEmployment", updatedEmployment);

                return ApiResponse.onSuccess(result);
            }

            @PostMapping("/income")
            @Operation(summary = "소득분위 등록 API", description = "유저 소득분위 등록")
            public ApiResponse<Map<String, Object>> updateIncome(
                    @RequestBody UserRequestDTO.IncomeDto requestDto,
                    @AuthUser Long userId
            ) {
                Income updatedIncome = userService.updateIncome(userId, requestDto);

                Map<String, Object> result = new HashMap<>();
                result.put("updatedIncome", updatedIncome);

                return ApiResponse.onSuccess(result);
            }

            @PostMapping("/region")
            @Operation(summary = "지역 등록 API", description = "정책을 보고 싶은 지역 등록")
            public ApiResponse<Map<String, Object>> updateRegion(
                    @RequestBody UserRequestDTO.RegionDto requestDto,
                    @AuthUser Long userId
            ) {
                Region updatedRegion = userService.updateRegion(userId, requestDto);

                Map<String, Object> result = new HashMap<>();
                result.put("updatedRegion", updatedRegion);

                return ApiResponse.onSuccess(result);
            }

            @PostMapping("/interest")
            @Operation(summary = "관심사 등록 API", description = "유저 관심사 등록")
            public ApiResponse<Map<String, Object>> updateInterest(
                    @RequestBody UserRequestDTO.InterestDto requestDto,
                    @AuthUser Long userId
            ) {
                List<String> updatedInterests = userService.updateInterest(userId, requestDto);

                Map<String, Object> result = new HashMap<>();
                result.put("updatedInterests", updatedInterests);

                return ApiResponse.onSuccess(result);
            }

            @PostMapping("/addition")
            @Operation(summary = "추가사항 등록 API", description = "유저 추가사항 등록")
            public ApiResponse<Map<String, Object>> updateAddition(
                    @RequestBody UserRequestDTO.AdditionDto requestDto,
                    @AuthUser Long userId
            ) {
                List<String> updatedAdditions = userService.updateAddition(userId, requestDto);

                Map<String, Object> result = new HashMap<>();
                result.put("updatedAdditions", updatedAdditions);

                return ApiResponse.onSuccess(result);
            }
        }
