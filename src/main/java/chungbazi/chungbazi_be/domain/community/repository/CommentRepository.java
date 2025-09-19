package chungbazi.chungbazi_be.domain.community.repository;

import chungbazi.chungbazi_be.domain.community.entity.Comment;
import chungbazi.chungbazi_be.domain.community.entity.ContentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 처음 요청
    Page<Comment> findByStatusAndAuthorIdNotInAndPostIdOrderByIdAsc(ContentStatus status, List<Long> excludedAuthorIds, Long postId, Pageable pageable);

    // 무한 스크롤
    Page<Comment> findByStatusAndAuthorIdNotInAndPostIdAndIdGreaterThanOrderByIdAsc(ContentStatus status, List<Long> excludedAuthorIds, Long postId, Long lastCommentId, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.id = :postId AND c.status = :status AND c.author.id NOT IN :excludedAuthorIds")
    Long countByPostIdAndStatusAndAuthorIdNotIn(@Param("postId") Long postId, @Param("status") ContentStatus status, @Param("excludedAuthorIds") List<Long> excludedAuthorIds);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.author.id= :authorId AND c.status = :status")
    int countCommentByAuthorIdAndStatus(@Param("authorId") Long authorId,@Param("status") ContentStatus status);
}
