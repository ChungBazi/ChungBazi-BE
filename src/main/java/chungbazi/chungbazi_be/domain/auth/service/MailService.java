package chungbazi.chungbazi_be.domain.auth.service;

import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.utils.UserHelper;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.BadRequestHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Random;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MailService {
    private static final String AUTH_CODE_PREFIX = "AuthCode ";

    private static final long authCodeExpirationMillis = 1000 * 60 * 30;

    private final TokenAuthService tokenAuthService;
    private final UserHelper userHelper;
    private final JavaMailSender emailSender;

    public void sendEmail(String toEmail,
                          String title,
                          String text) {
        SimpleMailMessage emailForm = createEmailForm(toEmail, title, text);
        try {
            emailSender.send(emailForm);
        } catch (RuntimeException e) {
            log.debug("MailService.sendEmail exception occur toEmail: {}, " +
                    "title: {}, text: {}", toEmail, title, text);
            throw new BadRequestHandler(ErrorStatus.UNABLE_TO_SEND_EMAIL);
        }
    }

    // 발신할 이메일 데이터 세팅
    private SimpleMailMessage createEmailForm(String toEmail,
                                              String title,
                                              String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(text);

        return message;
    }

    public void sendCodeToEmail() {
        User user = userHelper.getAuthenticatedUser();
        String toEmail = user.getEmail();
        String title = "청바지 이메일 인증 번호";
        String authCode = this.createCode();
        String content = String.format("""
            안녕하세요, 청바지입니다. 👖

            요청하신 이메일 인증을 위해 아래 인증번호를 입력해주세요.
            인증번호: %s

            감사합니다.
            """, authCode);
        sendEmail(toEmail, title, content);

        // 이메일 인증 요청 시 인증 번호 Redis에 저장 ( key = "AuthCode " + Email / value = AuthCode )
        tokenAuthService.setAuthCode(AUTH_CODE_PREFIX + toEmail,
                authCode, Duration.ofMillis(authCodeExpirationMillis));
    }

    private String createCode() {
        int length = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new BadRequestHandler(ErrorStatus.NO_SUCH_ALGORITHM);
        }
    }

    public void verifiedCode( String authCode) {
        User user = userHelper.getAuthenticatedUser();
        String email = user.getEmail();
        String redisAuthCode = tokenAuthService.getAuthCode(AUTH_CODE_PREFIX + email);
        boolean authResult = tokenAuthService.checkExistsAuthCode(redisAuthCode) && redisAuthCode.equals(authCode);
        if (!authResult) {
            throw new BadRequestHandler(ErrorStatus.INVALID_AUTHCODE);
        }
    }

}
