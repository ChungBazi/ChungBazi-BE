package chungbazi.chungbazi_be.domain.community.dto;

import chungbazi.chungbazi_be.domain.policy.entity.Category;
import chungbazi.chungbazi_be.domain.user.entity.enums.RewardLevel;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class CommunityResponseDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class UploadAndGetPostDto {
        Long postId;
        String title;
        String content;
        Category category;
        String formattedCreatedAt;
        boolean isMine;

        boolean anonymous; //익명 여부 추가

        Integer views;
        Long commentCount;
        Integer postLikes;

        Long userId;
        String userName;
        RewardLevel reward;
        RewardLevel characterImg;

        String thumbnailUrl;
        List<String> imageUrls;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    public static class TotalPostListDto {
        Long totalPostCount;
        List<PostListDto> postList;
        private Long nextCursor;
        private boolean hasNext;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PostListDto {
        Long postId;
        String title;
        String content;
        Category category;
        String formattedCreatedAt;
        boolean isMine;

        boolean anonymous;

        Integer views;
        Long commentCount;
        Integer postLikes;

        Long userId;
        String userName;
        RewardLevel reward;
        RewardLevel characterImg;

        String thumbnailUrl;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class UploadAndGetCommentDto {
        Long postId;
        String content;
        String formattedCreatedAt;

        Long commentId;

        Long userId;
        String userName;
        RewardLevel reward;
        RewardLevel characterImg;
        boolean isMine;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CommentListDto {
        List<UploadAndGetCommentDto> commentsList;
        private Long nextCursor;
        private boolean hasNext;
    }
}
