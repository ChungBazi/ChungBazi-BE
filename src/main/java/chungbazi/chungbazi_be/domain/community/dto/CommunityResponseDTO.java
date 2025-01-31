package chungbazi.chungbazi_be.domain.community.dto;

import chungbazi.chungbazi_be.domain.policy.entity.Category;
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

        Integer views;
        Long commentCount;

        Long userId;
        String userName;
        Integer reward;
        String characterImg;

        String thumbnailUrl;
        List<String> imageUrls;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    public static class TotalPostListDto {
        Long totalPostCount;
        List<PostListDto> postList;
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

        Integer views;
        Long commentCount;

        Long userId;
        String userName;
        Integer reward;
        String characterImg;

        String thumbnailUrl;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class UploadAndGetCommentDto {
        Long postId;
        String content;
        String formattedCreatedAt;

        Long userId;
        String userName;
        Integer reward;
        String characterImg;
    }
}
