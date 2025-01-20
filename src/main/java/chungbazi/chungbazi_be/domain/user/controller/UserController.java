package chungbazi.chungbazi_be.domain.user.controller;

//import chungbazi.chungbazi_be.domain.auth.AuthTokensGenerator;
import chungbazi.chungbazi_be.domain.user.dto.UserRequestDTO;
import chungbazi.chungbazi_be.domain.user.dto.UserResponseDTO;
import chungbazi.chungbazi_be.domain.user.entity.enums.Education;
import chungbazi.chungbazi_be.domain.user.entity.enums.Employment;
import chungbazi.chungbazi_be.domain.user.entity.enums.Income;
import chungbazi.chungbazi_be.domain.user.entity.enums.Region;
import chungbazi.chungbazi_be.domain.user.repository.*;
import chungbazi.chungbazi_be.domain.user.service.UserService;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    @Operation(summary = "프로필 조회 API", description = "마이페이지 프로필 조회")
    public ApiResponse<UserResponseDTO.ProfileDto> getProfile(){
        return ApiResponse.onSuccess(userService.getProfile());
    }

    @PostMapping("/education")
    public ResponseEntity<ApiResponse<?>> updateEducation(
            @RequestBody UserRequestDTO.EducationDto requestDto,
            @AuthenticationPrincipal JwtAuthentication authentication
    ) {
        log.debug("Authentication via @AuthenticationPrincipal: {}", authentication);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Authentication via SecurityContextHolder: {}", auth);

        if (!(auth instanceof JwtAuthentication jwtAuth)) {
            log.error("Authentication is null or invalid");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.onFailure(null, "Authentication required", null));
        }

        Long userId = Long.parseLong(jwtAuth.getName());
        log.debug("Updating education for userId: {}", userId);

        Education updatedEducation = userService.updateEducation(userId, requestDto);

        Map<String, Object> result = new HashMap<>();
        result.put("updatedEducation", updatedEducation);

        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

    @PostMapping("/employment")
    public ResponseEntity<ApiResponse<?>> updateEmployment(
            @RequestBody UserRequestDTO.EmploymentDto requestDto,
            @AuthenticationPrincipal JwtAuthentication authentication
    ) {
        log.debug("Authentication via @AuthenticationPrincipal: {}", authentication);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Authentication via SecurityContextHolder: {}", auth);

        if (!(auth instanceof JwtAuthentication jwtAuth)) {
            log.error("Authentication is null or invalid");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.onFailure(null, "Authentication required", null));
        }

        Long userId = Long.parseLong(jwtAuth.getName());
        log.debug("Updating employment for userId: {}", userId);

        Employment updatedEmployment = userService.updateEmployment(userId, requestDto);

        Map<String, Object> result = new HashMap<>();
        result.put("updatedEmployment", updatedEmployment);

        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

    @PostMapping("/income")
    public ResponseEntity<ApiResponse<?>> updateIncome(
            @RequestBody UserRequestDTO.IncomeDto requestDto,
            @AuthenticationPrincipal JwtAuthentication authentication
    ) {
        log.debug("Authentication via @AuthenticationPrincipal: {}", authentication);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Authentication via SecurityContextHolder: {}", auth);

        if (!(auth instanceof JwtAuthentication jwtAuth)) {
            log.error("Authentication is null or invalid");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.onFailure(null, "Authentication required", null));
        }

        Long userId = Long.parseLong(jwtAuth.getName());
        log.debug("Updating income for userId: {}", userId);

        Income updatedIncome = userService.updateIncome(userId, requestDto);

        Map<String, Object> result = new HashMap<>();
        result.put("updatedIncome", updatedIncome);

        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

    @PostMapping("/region")
    public ResponseEntity<ApiResponse<?>> updateRegion(
            @RequestBody UserRequestDTO.RegionDto requestDto,
            @AuthenticationPrincipal JwtAuthentication authentication
    ) {
        log.debug("Authentication via @AuthenticationPrincipal: {}", authentication);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Authentication via SecurityContextHolder: {}", auth);

        if (!(auth instanceof JwtAuthentication jwtAuth)) {
            log.error("Authentication is null or invalid");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.onFailure(null, "Authentication required", null));
        }

        Long userId = Long.parseLong(jwtAuth.getName());
        log.debug("Updating region for userId: {}", userId);

        Region updatedRegion = userService.updateRegion(userId, requestDto);

        Map<String, Object> result = new HashMap<>();
        result.put("updatedRegion", updatedRegion);

        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

    @PostMapping("/interest")
    public ResponseEntity<ApiResponse<?>> updateInterest(
            @RequestBody UserRequestDTO.InterestDto requestDto,
            @AuthenticationPrincipal JwtAuthentication authentication
    ) {
        log.debug("Authentication via @AuthenticationPrincipal: {}", authentication);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Authentication via SecurityContextHolder: {}", auth);

        if (!(auth instanceof JwtAuthentication jwtAuth)) {
            log.error("Authentication is null or invalid");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.onFailure(null, "Authentication required", null));
        }

        Long userId = Long.parseLong(jwtAuth.getName());
        log.debug("Updating interests for userId: {}", userId);

        List<String> updatedInterests = userService.updateInterest(userId, requestDto);

        Map<String, Object> result = new HashMap<>();
        result.put("updatedInterests", updatedInterests);

        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

    @PostMapping("/addition")
    public ResponseEntity<ApiResponse<?>> updateAddition(
            @RequestBody UserRequestDTO.AdditionDto requestDto,
            @AuthenticationPrincipal JwtAuthentication authentication
    ) {
        log.debug("Authentication via @AuthenticationPrincipal: {}", authentication);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Authentication via SecurityContextHolder: {}", auth);

        if (!(auth instanceof JwtAuthentication jwtAuth)) {
            log.error("Authentication is null or invalid");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.onFailure(null, "Authentication required", null));
        }

        Long userId = Long.parseLong(jwtAuth.getName());
        log.debug("Updating additions for userId: {}", userId);

        List<String> updatedAdditions = userService.updateAddition(userId, requestDto);

        Map<String, Object> result = new HashMap<>();
        result.put("updatedAdditions", updatedAdditions);

        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }
}