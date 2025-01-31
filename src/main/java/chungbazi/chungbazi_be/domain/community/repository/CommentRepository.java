package chungbazi.chungbazi_be.domain.community.repository;

import chungbazi.chungbazi_be.domain.community.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 처음 요청
    Page<Comment> findByPostIdOrderByIdDesc(Long postId, Pageable pageable);

    // 무한 스크롤
    Page<Comment> findByPostIdAndIdLessThanOrderByIdDesc(Long postId, Long lastCommentId, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.id = :postId")
    Long countByPostId(@Param("postId") Long postId);
}
