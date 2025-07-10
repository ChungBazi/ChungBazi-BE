package chungbazi.chungbazi_be.domain.community.converter;

import chungbazi.chungbazi_be.domain.community.dto.CommunityResponseDTO;
import chungbazi.chungbazi_be.domain.community.dto.CommunityResponseDTO.CommentListDto;
import chungbazi.chungbazi_be.domain.community.entity.Comment;
import chungbazi.chungbazi_be.domain.community.entity.Post;
import chungbazi.chungbazi_be.domain.community.repository.CommentRepository;
import java.util.List;

public class CommunityConverter {

    public static CommunityResponseDTO.TotalPostListDto toTotalPostListDto(
            Long totalPostCount, List<CommunityResponseDTO.PostListDto> postList, Long nextCursor, boolean hasNext){
        return CommunityResponseDTO.TotalPostListDto.builder()
                .totalPostCount(totalPostCount)
                .postList(postList)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }
    public static List<CommunityResponseDTO.PostListDto> toPostListDto(List<Post> posts, CommentRepository commentRepository) {
        return posts.stream().map(post ->{
            Long commentCount = commentRepository.countByPostId(post.getId());
            return CommunityResponseDTO.PostListDto.builder()
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
                    .commentCount(commentCount)
                    .category(post.getCategory())
                    .postLikes(post.getPostLikes())
                    .anonymous(post.isAnonymous())
                .build();
        }).toList();
    }

    public static CommunityResponseDTO.UploadAndGetPostDto toUploadAndGetPostDto(Post post, Long commentCount) {
        return CommunityResponseDTO.UploadAndGetPostDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .formattedCreatedAt(post.getFormattedCreatedAt())
                .views(post.getViews())
                .postLikes(post.getPostLikes())
                .commentCount(commentCount)
                .userId(post.getAuthor().getId())
                .userName(post.getAuthor().getName())
                .reward(post.getAuthor().getReward())
                .characterImg(post.getAuthor().getCharacterImg())
                .thumbnailUrl(post.getThumbnailUrl())
                .imageUrls(post.getImageUrls())
                .postLikes(post.getPostLikes())
                .anonymous(post.isAnonymous())
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
                .commentId(comment.getId())
                .build();
    }
    public static List<CommunityResponseDTO.UploadAndGetCommentDto> toListCommentDto(List<Comment> comments){
        return comments.stream()
                .map(CommunityConverter::toUploadAndGetCommentDto)
                .toList();
    }
    public static CommunityResponseDTO.CommentListDto toGetCommentsListDto(List<CommunityResponseDTO.UploadAndGetCommentDto> commentsList, Long nextCursor, boolean hasNext) {
        return CommunityResponseDTO.CommentListDto.builder()
                .commentsList(commentsList)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }
}
