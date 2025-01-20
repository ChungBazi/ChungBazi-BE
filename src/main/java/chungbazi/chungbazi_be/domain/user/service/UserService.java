package chungbazi.chungbazi_be.domain.user.service;


import chungbazi.chungbazi_be.domain.user.UserHandler;
import chungbazi.chungbazi_be.domain.user.converter.UserConverter;
import chungbazi.chungbazi_be.domain.user.dto.UserRequestDTO;
import chungbazi.chungbazi_be.domain.user.dto.UserResponseDTO;
import chungbazi.chungbazi_be.domain.user.entity.Addition;
import chungbazi.chungbazi_be.domain.user.entity.Interest;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.entity.enums.Education;
import chungbazi.chungbazi_be.domain.user.entity.enums.Employment;
import chungbazi.chungbazi_be.domain.user.entity.enums.Income;
import chungbazi.chungbazi_be.domain.user.entity.enums.Region;
import chungbazi.chungbazi_be.domain.user.entity.mapping.UserAddition;
import chungbazi.chungbazi_be.domain.user.entity.mapping.UserInterest;
import chungbazi.chungbazi_be.domain.user.repository.*;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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
                .orElseThrow(() -> new UserHandler(ErrorStatus.NOT_FOUND_USER));

        return UserConverter.toProfileDto(user);
    }

    public Education updateEducation(Long userId, UserRequestDTO.EducationDto requestDto) {
        log.debug("Finding user by id: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NOT_FOUND_USER));
        log.debug("Found user: {}", user);

        user.updateEducation(requestDto.getEducation());
        userRepository.save(user);

        log.debug("Education updated to: {}", requestDto.getEducation());
        return requestDto.getEducation();
    }

    public Employment updateEmployment(Long userId, UserRequestDTO.EmploymentDto requestDto) {
        log.debug("Finding user by id: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NOT_FOUND_USER));
        log.debug("Found user: {}", user);

        user.updateEmployment(requestDto.getEmployment());
        userRepository.save(user);

        log.debug("Employment updated to: {}", requestDto.getEmployment());
        return user.getEmployment();
    }

    public Income updateIncome(Long userId, UserRequestDTO.IncomeDto requestDto) {
        log.debug("Finding user by id: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NOT_FOUND_USER));
        log.debug("Found user: {}", user);

        user.updateIncome(requestDto.getIncome());
        userRepository.save(user);

        log.debug("Income updated to: {}", requestDto.getIncome());
        return user.getIncome();
    }

    public Region updateRegion(Long userId, UserRequestDTO.RegionDto requestDto) {
        log.debug("Finding user by id: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NOT_FOUND_USER));
        log.debug("Found user: {}", user);

        user.updateRegion(requestDto.getRegion());
        userRepository.save(user);

        log.debug("Region updated to: {}", requestDto.getRegion());
        return user.getRegion();
    }


    public List<String> updateInterest(Long userId, UserRequestDTO.InterestDto requestDto) {
        log.debug("Finding user by id: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NOT_FOUND_USER));
        log.debug("Found user: {}", user);

        log.debug("Deleting existing interests for user: {}", userId);
        userInterestRepository.deleteByUser(user);

        List<String> interests = requestDto.getInterests();
        for (String interestName : interests) {
            Interest interest = interestRepository.findByName(interestName)
                    .orElseGet(() -> {
                        log.debug("Creating new interest: {}", interestName);
                        return interestRepository.save(Interest.from(interestName));
                    });

            UserInterest userInterest = UserInterest.builder()
                    .user(user)
                    .interest(interest)
                    .build();
            userInterestRepository.save(userInterest);
            log.debug("Saved interest: {} for user: {}", interestName, userId);
        }

        log.debug("Updated interests for user: {} to {}", userId, interests);
        return interests;
    }

    public List<String> updateAddition(Long userId, UserRequestDTO.AdditionDto requestDto) {
        log.debug("Finding user by id: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NOT_FOUND_USER));
        log.debug("Found user: {}", user);

        log.debug("Deleting existing additional info for user: {}", userId);
        userAdditionRepository.deleteByUser(user);

        List<String> additionalInfo = requestDto.getAdditionInfo();
        for (String additionName : additionalInfo) {
            Addition addition = additionRepository.findByName(additionName)
                    .orElseGet(() -> {
                        log.debug("Creating new addition: {}", additionName);
                        return additionRepository.save(Addition.from(additionName));
                    });

            UserAddition userAddition = UserAddition.builder()
                    .user(user)
                    .addition(addition)
                    .build();
            userAdditionRepository.save(userAddition);
            log.debug("Saved addition: {} for user: {}", additionName, userId);
        }

        log.debug("Updated additional info for user: {} to {}", userId, additionalInfo);
        return additionalInfo;
    }
}
