package chungbazi.chungbazi_be.global.validation.validator;

import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.validation.annotation.ExistEntity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityExistValidator implements ConstraintValidator<ExistEntity, Long> {
    private final Map<Class<?>, JpaRepository<?, Long>> repositoryMap;
    private final Map<Class<?>, ErrorStatus> errorStatusMap;

    private Class<?> entityType;

    @Override
    public void initialize(ExistEntity constraintAnnotation) {this.entityType = constraintAnnotation.entityType();}

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("id는 필수입니다.").addConstraintViolation();
            return false;
        }

        JpaRepository<?, Long> repository = repositoryMap.get(entityType);
        if (repository == null) {
            throw new IllegalArgumentException("Repository not found for entity type: " + entityType.getName());
        }

        boolean exists = repository.existsById(value);

        if (!exists) {
            context.disableDefaultConstraintViolation();

            //동적 메세지 설정
            String codeName = errorStatusMap.get(entityType).name();
            context.buildConstraintViolationWithTemplate(codeName).addConstraintViolation();
        }

        return exists;
    }
}
