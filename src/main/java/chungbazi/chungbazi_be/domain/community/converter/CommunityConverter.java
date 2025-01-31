package chungbazi.chungbazi_be.domain.community.converter;

import chungbazi.chungbazi_be.domain.community.dto.CommunityResponseDTO;
import chungbazi.chungbazi_be.domain.community.entity.Comment;
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

    public static CommunityResponseDTO.UploadAndGetPostDto toUploadAndGetPostDto(Post post) {
        return CommunityResponseDTO.UploadAndGetPostDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .formattedCreatedAt(post.getFormattedCreatedAt())
                .views(post.getViews())
                .userId(post.getAuthor().getId())
                .userName(post.getAuthor().getName())
                .reward(post.getAuthor().getReward())
                .characterImg(post.getAuthor().getCharacterImg())
                .thumbnailUrl(post.getThumbnailUrl())
                .imageUrls(post.getImageUrls())
                .build();
    }

    public static CommunityResponseDTO.UploadAndGetCommentDto toUploadAndGetCommentDto(Comment comment) {
        return CommunityResponseDTO.UploadAndGetCommentDto.builder()
                .postId(comment.getPost().getId())
                .content(comment.getContent())
                .formattedCreatedAt(comment.getFormattedCreatedAt())
                .userId(comment.getAuthor().getId())
                .userName(comment.getAuthor().getName())
                .reward(comment.getAuthor().getReward())
                .characterImg(comment.getAuthor().getCharacterImg())
                .build();
    }
    public static List<CommunityResponseDTO.UploadAndGetCommentDto> toGetListCommentDto(List<Comment> comments){
        return comments.stream()
                .map(CommunityConverter::toUploadAndGetCommentDto)
                .toList();
    }
}
