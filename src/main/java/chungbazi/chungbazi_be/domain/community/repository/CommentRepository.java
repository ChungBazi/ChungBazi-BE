package chungbazi.chungbazi_be.domain.community.repository;

import chungbazi.chungbazi_be.domain.community.entity.Comment;
import chungbazi.chungbazi_be.domain.community.entity.ContentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>,CommentRepositoryCustom {

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.author.id= :authorId AND c.status = :status")
    int countCommentByAuthorIdAndStatus(@Param("authorId") Long authorId,@Param("status") ContentStatus status);
}
