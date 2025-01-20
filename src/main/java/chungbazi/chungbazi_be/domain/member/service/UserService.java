package chungbazi.chungbazi_be.domain.member.service;


import chungbazi.chungbazi_be.domain.member.UserHandler;
import chungbazi.chungbazi_be.domain.member.converter.UserConverter;
import chungbazi.chungbazi_be.domain.member.dto.UserRequestDTO;
import chungbazi.chungbazi_be.domain.member.dto.UserResponseDTO;
import chungbazi.chungbazi_be.domain.member.entity.Addition;
import chungbazi.chungbazi_be.domain.member.entity.Interest;
import chungbazi.chungbazi_be.domain.member.entity.User;
import chungbazi.chungbazi_be.domain.member.entity.enums.Education;
import chungbazi.chungbazi_be.domain.member.entity.enums.Employment;
import chungbazi.chungbazi_be.domain.member.entity.enums.Income;
import chungbazi.chungbazi_be.domain.member.entity.enums.Region;
import chungbazi.chungbazi_be.domain.member.entity.mapping.UserAddition;
import chungbazi.chungbazi_be.domain.member.entity.mapping.UserInterest;
import chungbazi.chungbazi_be.domain.member.repository.*;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AdditionRepository additionRepository;
    private final UserAdditionRepository userAdditionRepository;
    private final InterestRepository interestRepository;
    private final UserInterestRepository userInterestRepository;

    public UserResponseDTO.ProfileDto getProfile() {
        // 추후 시큐리티로 id 가져오기 (하드 코딩된 상태)
        Long userId = 1L;

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));

        return UserConverter.toProfileDto(user);
    }


    public Education updateEducationForCurrentUser(Long memberId, UserRequestDTO.EducationDto requestDto) {
        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));
        user.updateEducation(requestDto.getEducation());
        return user.getEducation();
    }

    public Employment updateEmploymentForCurrentUser(Long memberId, UserRequestDTO.EmploymentDto requestDto) {
        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));
        user.updateEmployment(requestDto.getEmployment());
        return user.getEmployment();
    }

    public Income updateIncomeForCurrentUser(Long memberId, UserRequestDTO.IncomeDto requestDto) {
        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));
        user.updateIncome(requestDto.getIncome());
        return user.getIncome();
    }

    public Region updateRegionForCurrentUser(Long memberId, UserRequestDTO.RegionDto requestDto) {
        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));
        user.updateRegion(requestDto.getRegion());
        return user.getRegion();
    }


    public List<String> updateInterestForCurrentUser(Long memberId, UserRequestDTO.InterestDto requestDto) {
        List<String> interests = requestDto.getInterests();
        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));

        // 기존 관심사 삭제
        userInterestRepository.deleteByMember(user);

        // 새로운 관심사 저장
        for (String interestName : interests) {
            Interest interest = interestRepository.findByName(interestName)
                    .orElseGet(() -> interestRepository.save(Interest.from(interestName)));

            UserInterest userInterest = UserInterest.builder()
                    .user(user)
                    .interest(interest)
                    .build();
            userInterestRepository.save(userInterest);
        }

        return interests;
    }


    public List<String> updateAdditionForCurrentUser(Long memberId, UserRequestDTO.AdditionDto requestDto) {
        List<String> additionalInfo = requestDto.getAdditionInfo();
        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));

        // 기존 추가 정보 삭제
        userAdditionRepository.deleteByMember(user);

        // 새로운 추가 정보 저장
        for (String additionName : additionalInfo) {
            Addition addition = additionRepository.findByName(additionName)
                    .orElseGet(() -> additionRepository.save(Addition.from(additionName)));

            UserAddition userAddition = UserAddition.builder()
                    .user(user)
                    .addition(addition)
                    .build();
            userAdditionRepository.save(userAddition);
        }

        return additionalInfo;
    }
}
