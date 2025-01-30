package chungbazi.chungbazi_be.domain.community.dto;

import chungbazi.chungbazi_be.domain.community.entity.CommunityImages;
import chungbazi.chungbazi_be.domain.policy.entity.Category;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class CommunityResponseDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class UploadPostDto {
        Long postId;
        String title;
        String content;
        List<CommunityImages> communityImagesList;
        Category category;
        String formattedCreatedAt;

        Integer views;

        Long userId;
        String userName;
        Integer reward;
        String characterImg;
    }
}
