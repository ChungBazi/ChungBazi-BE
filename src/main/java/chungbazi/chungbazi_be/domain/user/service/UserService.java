package chungbazi.chungbazi_be.domain.user.service;


import chungbazi.chungbazi_be.domain.auth.jwt.SecurityUtils;
import chungbazi.chungbazi_be.domain.user.converter.UserConverter;
import chungbazi.chungbazi_be.domain.user.dto.UserRequestDTO;
import chungbazi.chungbazi_be.domain.user.dto.UserResponseDTO;
import chungbazi.chungbazi_be.domain.user.entity.Addition;
import chungbazi.chungbazi_be.domain.user.entity.Interest;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.entity.mapping.UserAddition;
import chungbazi.chungbazi_be.domain.user.entity.mapping.UserInterest;
import chungbazi.chungbazi_be.domain.user.repository.*;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Long userId = SecurityUtils.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));

        return UserConverter.toProfileDto(user);
    }

    public void registerUserInfo(UserRequestDTO.RegisterDto registerDto) {
        Long userId = SecurityUtils.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));

        user.updateRegion(registerDto.getRegion());
        user.updateEmployment(registerDto.getEmployment());
        user.updateIncome(registerDto.getIncome());
        user.updateEducation(registerDto.getEducation());
        updateInterests(user, registerDto.getInterests());
        updateAdditions(user, registerDto.getAdditionInfo());
        user.setSurveyStatus(true);
    }

    public void updateUserInfo(UserRequestDTO.UpdateDto updateDto) {
        Long userId = SecurityUtils.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));
        Optional.ofNullable(updateDto.getRegion()).ifPresent(user::updateRegion);
        Optional.ofNullable(updateDto.getEmployment()).ifPresent(user::updateEmployment);
        Optional.ofNullable(updateDto.getIncome()).ifPresent(user::updateIncome);
        Optional.ofNullable(updateDto.getEducation()).ifPresent(user::updateEducation);
        Optional.ofNullable(updateDto.getInterests()).ifPresent(interests -> updateInterests(user, interests));
        Optional.ofNullable(updateDto.getAdditionInfo()).ifPresent(additions -> updateAdditions(user, additions));
    }

    private void updateAdditions(User user, List<String> additionalInfo) {
        userAdditionRepository.deleteByUser(user);
        for (String additionName : additionalInfo) {
            Addition addition = additionRepository.findByName(additionName)
                    .orElseGet(() -> additionRepository.save(Addition.from(additionName)));
            userAdditionRepository.save(UserAddition.builder().user(user).addition(addition).build());
        }
    }


    private void updateInterests(User user, List<String> interests) {
        userInterestRepository.deleteByUser(user);
        for (String interestName : interests) {
            Interest interest = interestRepository.findByName(interestName)
                    .orElseGet(() -> interestRepository.save(Interest.from(interestName)));
            userInterestRepository.save(UserInterest.builder().user(user).interest(interest).build());
        }
    }
}