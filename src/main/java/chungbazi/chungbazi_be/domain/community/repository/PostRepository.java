package chungbazi.chungbazi_be.domain.community.repository;

import chungbazi.chungbazi_be.domain.community.entity.ContentStatus;
import chungbazi.chungbazi_be.domain.community.entity.Post;
import chungbazi.chungbazi_be.domain.policy.entity.Category;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 전체 게시글 처음 요청
    Page<Post> findByStatusAndAuthorIdNotInOrderByIdDesc(ContentStatus status, List<Long> excludedAuthorIds, Pageable pageable);

    // 전체 게시글 무한 스크롤
    Page<Post> findByStatusAndAuthorIdNotInAndIdLessThanOrderByIdDesc(ContentStatus status, List<Long> excludedAuthorIds, Long lastPostId, Pageable pageable);

    // 카테고리별 처음 요청
    Page<Post> findByCategoryAndStatusAndAuthorIdNotInOrderByIdDesc(Category category, ContentStatus status, List<Long> excludedAuthorIds,Pageable pageable);

    // 카테고리별 무한 스크롤
    Page<Post> findByCategoryAndStatusAndAuthorIdNotInAndIdLessThanOrderByIdDesc(Category category, ContentStatus status, List<Long> excludedAuthorIds, Long lastPostId, Pageable pageable);

    // 카테고리별 게시글 수
    @Query("SELECT COUNT(p) FROM Post p WHERE (:category IS NULL OR p.category = :category) AND p.status = :status AND p.author.id NOT IN :excludedAuthorIds")
    Long countPostByCategoryAndStatusAndAuthorIdNotIn(@Param("category") Category category, @Param("status") ContentStatus status, @Param("excludedAuthorIds") List<Long> excludedAuthorIds);

    //user의 게시글 수
    @Query("SELECT  COUNT(p) FROM Post p WHERE p.author.id= :authorId ")
    int countPostByAuthorId(@Param("authorId") Long authorId);
    //제목으로 검색
    Page<Post> findByStatusAndAuthorIdNotInAndTitleContainingAndCreatedAtAfterOrderByIdDesc(ContentStatus status, List<Long> excludedAuthorIds, String title, LocalDateTime startDate, Pageable pageable);
    Page<Post> findByStatusAndAuthorIdNotInAndTitleContainingAndCreatedAtAfterAndIdLessThanOrderByIdDesc(ContentStatus status, List<Long> excludedAuthorIds, String title, LocalDateTime startDate, Long cursor, Pageable pageable);
    //내용으로 검색
    Page<Post> findByStatusAndAuthorIdNotInAndContentContainingAndCreatedAtAfterOrderByIdDesc(ContentStatus status, List<Long> excludedAuthorIds, String title, LocalDateTime startDate, Pageable pageable);
    Page<Post> findByStatusAndAuthorIdNotInAndContentContainingAndCreatedAtAfterAndIdLessThanOrderByIdDesc(ContentStatus status, List<Long> excludedAuthorIds, String title, LocalDateTime startDate, Long cursor, Pageable pageable);


}
