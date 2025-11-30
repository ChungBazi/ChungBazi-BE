package chungbazi.chungbazi_be.domain.community.repository;

import chungbazi.chungbazi_be.domain.community.entity.Comment;
import chungbazi.chungbazi_be.domain.community.entity.ContentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepositoryCustom {

    Page<Comment> findCommentsWithFilters(
            ContentStatus status,
            List<Long> excludedAuthorIds,
            List<Long> reportedCommentIds,
            Long postId,
            Long lastCommentId,
            Pageable pageable
    );

    Long countCommentsWithFilters(Long postId,
                                  ContentStatus status,
                                  List<Long> excludedAuthorIds,
                                  List<Long> reportedCommentIds);
}
