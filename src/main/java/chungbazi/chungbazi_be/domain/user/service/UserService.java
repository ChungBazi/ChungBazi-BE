package chungbazi.chungbazi_be.domain.user.service;


import chungbazi.chungbazi_be.domain.auth.jwt.SecurityUtils;
import chungbazi.chungbazi_be.domain.user.converter.UserConverter;
import chungbazi.chungbazi_be.domain.user.dto.UserRequestDTO;
import chungbazi.chungbazi_be.domain.user.dto.UserResponseDTO;
import chungbazi.chungbazi_be.domain.user.entity.Addition;
import chungbazi.chungbazi_be.domain.user.entity.Interest;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.entity.enums.RewardLevel;
import chungbazi.chungbazi_be.domain.user.entity.mapping.UserAddition;
import chungbazi.chungbazi_be.domain.user.entity.mapping.UserInterest;
import chungbazi.chungbazi_be.domain.user.repository.*;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import chungbazi.chungbazi_be.global.s3.S3Manager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final S3Manager s3Manager;

    public UserResponseDTO.ProfileDto getProfile() {
        Long userId = SecurityUtils.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));

        return UserConverter.toProfileDto(user);
    }
    public UserResponseDTO.CharacterListDto getCharacters() {
        Long userId = SecurityUtils.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));

        return UserConverter.toCharacterListDto(user);
    }

    public UserResponseDTO.ProfileUpdateDto updateProfile(UserRequestDTO.ProfileUpdateDto profileUpdateDto, MultipartFile profileImg) {
        final long MAX_UPLOAD_SIZE = 5 * 1024 * 1024;

        //user, nickname handle
        Long userId = SecurityUtils.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));

        // 닉네임 변경 여부 확인
        if (!user.getName().equals(profileUpdateDto.getName())) { // 기존 닉네임과 입력받은 닉네임이 다를 경우
            boolean isDuplicateName = userRepository.existsByName(profileUpdateDto.getName());
            if (isDuplicateName) {
                throw new BadRequestHandler(ErrorStatus.INVALID_NICKNAME);
            }
            user.setName(profileUpdateDto.getName()); // 닉네임 변경
        }

        // profileImg handle
//        String profileUrl;
//
//        if(profileImg != null){
//            if(profileImg.getSize() > MAX_UPLOAD_SIZE){
//                throw new BadRequestHandler(ErrorStatus.PAYLOAD_TOO_LARGE);
//            }
//            if(user.getProfileImg() != null){
//                s3Manager.deleteFile(user.getProfileImg());
//            }
//            profileUrl = s3Manager.uploadFile(profileImg, "profile-images");
//
//        } else {
//            profileUrl = user.getProfileImg();
//        }
//
//        user.setProfileImg(profileUrl);
        user.setName(profileUpdateDto.getName());
        userRepository.save(user);

        return UserConverter.toProfileUpdateDto(user);
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