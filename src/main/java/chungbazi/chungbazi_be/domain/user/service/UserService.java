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
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import chungbazi.chungbazi_be.global.s3.S3Manager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

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
    public UserResponseDTO.ProfileUpdateDto updateProfile(UserRequestDTO.ProfileUpdateDto profileUpdateDto, MultipartFile profileImg) {
        final long MAX_UPLOAD_SIZE = 5 * 1024 * 1024;

        //user, nickname handle
        Long userId = SecurityUtils.getUserId();

        boolean isDuplicateName = userRepository.existsByName(profileUpdateDto.getName());
        if(isDuplicateName) {
            throw new BadRequestHandler(ErrorStatus.INVALID_NICKNAME);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));

        // profileImg handle
        String profileUrl;

        if(profileImg != null){
            if(profileImg.getSize() > MAX_UPLOAD_SIZE){
                throw new BadRequestHandler(ErrorStatus.PAYLOAD_TOO_LARGE);
            }
            if(user.getProfileImg() != null){
                s3Manager.deleteFile(user.getProfileImg());
            }
            profileUrl = s3Manager.uploadFile(profileImg, "profile-images");

        } else {
            profileUrl = user.getProfileImg();
        }

        user.setProfileImg(profileUrl);
        user.setName(profileUpdateDto.getName());
        userRepository.save(user);

        return UserConverter.toProfileUpdateDto(user);
    }

    public void registerUserInfo(Long userId, UserRequestDTO.RegisterDto registerDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));

        updateBasicInfo(user, registerDto);
        updateInterests(user, registerDto.getInterests());
        updateAdditions(user, registerDto.getAdditionInfo());
    }

    public void updateUserInfo(Long userId, UserRequestDTO.RegisterDto registerDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));

        updateBasicInfo(user, registerDto);
        updateInterests(user, registerDto.getInterests());
        updateAdditions(user, registerDto.getAdditionInfo());
    }

    private void updateBasicInfo(User user, UserRequestDTO.RegisterDto registerDto) {
        if (registerDto.getRegion() != null) {
            user.updateRegion(registerDto.getRegion());
        }
        if (registerDto.getEmployment() != null) {
            user.updateEmployment(registerDto.getEmployment());
        }
        if (registerDto.getIncome() != null) {
            user.updateIncome(registerDto.getIncome());
        }
        if (registerDto.getEducation() != null) {
            user.updateEducation(registerDto.getEducation());
        }

        userRepository.save(user);
    }

    private void updateAdditions(User user, List<String> additionalInfo) {
        if (additionalInfo == null) {
            return;
        }

        userAdditionRepository.deleteByUser(user);
        for (String additionName : additionalInfo) {
            Addition addition = additionRepository.findByName(additionName)
                    .orElseGet(() -> additionRepository.save(Addition.from(additionName)));
            userAdditionRepository.save(UserAddition.builder().user(user).addition(addition).build());
        }
    }


    private void updateInterests(User user, List<String> interests) {
        if (interests == null) {
            return;
        }

        userInterestRepository.deleteByUser(user);
        for (String interestName : interests) {
            Interest interest = interestRepository.findByName(interestName)
                    .orElseGet(() -> interestRepository.save(Interest.from(interestName)));
            userInterestRepository.save(UserInterest.builder().user(user).interest(interest).build());
        }
    }
}