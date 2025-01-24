package chungbazi.chungbazi_be.domain.user.service;


import chungbazi.chungbazi_be.global.apiPayload.exception.handler.UserHandler;
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

    public UserResponseDTO.ProfileDto getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NOT_FOUND_USER));

        return UserConverter.toProfileDto(user);
    }

    public Education updateEducation(Long userId, UserRequestDTO.EducationDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NOT_FOUND_USER));

        user.updateEducation(requestDto.getEducation());
        userRepository.save(user);

        return requestDto.getEducation();
    }

    public Employment updateEmployment(Long userId, UserRequestDTO.EmploymentDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NOT_FOUND_USER));

        user.updateEmployment(requestDto.getEmployment());
        userRepository.save(user);

        return user.getEmployment();
    }

    public Income updateIncome(Long userId, UserRequestDTO.IncomeDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NOT_FOUND_USER));

        user.updateIncome(requestDto.getIncome());
        userRepository.save(user);

        return user.getIncome();
    }

    public Region updateRegion(Long userId, UserRequestDTO.RegionDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NOT_FOUND_USER));

        user.updateRegion(requestDto.getRegion());
        userRepository.save(user);

        return user.getRegion();
    }


    public List<String> updateInterest(Long userId, UserRequestDTO.InterestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NOT_FOUND_USER));

        userInterestRepository.deleteByUser(user);

        List<String> interests = requestDto.getInterests();
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

    public List<String> updateAddition(Long userId, UserRequestDTO.AdditionDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NOT_FOUND_USER));

        userAdditionRepository.deleteByUser(user);

        List<String> additionalInfo = requestDto.getAdditionInfo();
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
