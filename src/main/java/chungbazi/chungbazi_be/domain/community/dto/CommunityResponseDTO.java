package chungbazi.chungbazi_be.domain.community.dto;

import chungbazi.chungbazi_be.domain.community.entity.ContentStatus;
import chungbazi.chungbazi_be.domain.policy.entity.Category;
import chungbazi.chungbazi_be.domain.user.entity.enums.RewardLevel;

import java.util.ArrayList;
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
        ContentStatus status;

        boolean anonymous; //익명 여부 추가

        Integer views;
        Long commentCount;
        Integer postLikes;
        boolean isLikedByUser;

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
        boolean isLikedByUser;

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
        Integer likesCount;
        boolean isLikedByUser;
        boolean isMine;
        Long parentCommentId;
        boolean isDeleted;

        int replyCount;
        List<UploadAndGetCommentDto> comments = new ArrayList<>();
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
