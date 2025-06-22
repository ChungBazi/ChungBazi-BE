package chungbazi.chungbazi_be.domain.user.service;

import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.entity.UserBlock;
import chungbazi.chungbazi_be.domain.user.repository.UserBlockRepository.UserBlockRepository;
import chungbazi.chungbazi_be.domain.user.repository.UserRepository;
import chungbazi.chungbazi_be.domain.user.utils.UserHelper;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.GeneralException;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserBlockService {
    private final UserHelper userHelper;
    private final UserRepository userRepository;
    private final UserBlockRepository userBlockRepository;

    public void blockUser(Long blockedUserId){
        User blocker = userHelper.getAuthenticatedUser();

        if(blockedUserId.equals(blocker.getId())){
            throw new GeneralException(ErrorStatus.INVALID_BLOCK);
        } else if (userBlockRepository.findByBlockerIdAndBlockedIdAndIsActiveTrue(blocker.getId(), blockedUserId).isPresent()){
            throw new GeneralException(ErrorStatus.ALEADY_BLOCKED);
        }

        User blockedUser = userRepository.findById(blockedUserId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USER));

        UserBlock userBlock = UserBlock.builder()
                .blocker(blocker)
                .blocked(blockedUser)
                .isActive(true)
                .build();

        userBlockRepository.save(userBlock);

    }

    public void unblockUser(Long blockedUserId){
        User blocker = userHelper.getAuthenticatedUser();

        UserBlock userBlock = userBlockRepository.findByBlockerIdAndBlockedIdAndIsActiveTrue(blocker.getId(),blockedUserId)
                .orElseThrow(() -> new NotFoundHandler(ErrorStatus.NOT_FOUND_USERBLOCK));

        userBlock.unblock();
        userBlockRepository.save(userBlock);
    }
}
