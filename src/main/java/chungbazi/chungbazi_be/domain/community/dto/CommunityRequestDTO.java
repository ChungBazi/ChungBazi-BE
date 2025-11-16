package chungbazi.chungbazi_be.domain.community.dto;

import chungbazi.chungbazi_be.domain.policy.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommunityRequestDTO {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UploadPostDto {
        @NotBlank
        @Size(min = 1, max = 30, message = "제목은 30자 이하")
        String title;

        @NotBlank
        @Size(min = 1, max = 1000, message = "내용은 1000자 이하")
        String content;

        @NotNull(message = "카테고리는 필수입니다.")
        Category category;

        boolean anonymous;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UploadCommentDto {
        @NotNull
        Long postId;

        @NotBlank
        @Size(min = 1, max = 100, message = "댓글은 100자 이하")
        String content;

        @Schema(description = "대댓글일 때의, 부모 댓글 id", example = "1")
        private Long parentCommentId;
    }
}
