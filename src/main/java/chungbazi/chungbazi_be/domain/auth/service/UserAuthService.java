package chungbazi.chungbazi_be.domain.auth.service;

import chungbazi.chungbazi_be.domain.auth.dto.TokenRequestDTO;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.repository.UserRepository;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAuthService {
    
    private final UserRepository userRepository;
    public User findOrCreateMember(TokenRequestDTO.LoginTokenRequestDTO request) {
        return userRepository.findByEmail(request.getEmail())
                .map(existingUser -> {
                    if (existingUser.isDeleted()) {
                        throw new BadRequestHandler(ErrorStatus.DEACTIVATED_ACCOUNT);
                    }
                    return existingUser;
                })
                .orElseGet(() -> createNewUser(request));
    }
    public User createNewUser(TokenRequestDTO.LoginTokenRequestDTO request) {
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .build();
        return userRepository.save(user);
    }

    public boolean determineIsFirst(User user) {
        return !user.isSurveyStatus();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));
        // 유저 익명화 (username & email 무력화)
        userRepository.anonymizeUser(userId);
    }
}
