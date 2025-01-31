package chungbazi.chungbazi_be.domain.community.converter;

import chungbazi.chungbazi_be.domain.community.dto.CommunityResponseDTO;
import chungbazi.chungbazi_be.domain.community.entity.Post;
import java.util.List;

public class CommunityConverter {
    public static List<CommunityResponseDTO.PostListDto> toPostListDto(List<Post> posts) {
        return posts.stream().map(post -> CommunityResponseDTO.PostListDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .formattedCreatedAt(post.getFormattedCreatedAt())
                .views(post.getViews())
                .thumbnailUrl(post.getThumbnailUrl())
                .userId(post.getAuthor().getId())
                .userName(post.getAuthor().getName())
                .reward(post.getAuthor().getReward())
                .characterImg(post.getAuthor().getCharacterImg())
                .build()).toList();
    }
}
