package chungbazi.chungbazi_be.domain.community.repository;

import chungbazi.chungbazi_be.domain.community.entity.ContentStatus;
import chungbazi.chungbazi_be.domain.community.entity.Post;
import chungbazi.chungbazi_be.domain.policy.entity.Category;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 전체 게시글 처음 요청
    Page<Post> findByStatusOrderByIdDesc(ContentStatus status, Pageable pageable);

    // 전체 게시글 무한 스크롤
    Page<Post> findByStatusAndIdLessThanOrderByIdDesc(ContentStatus status,Long lastPostId, Pageable pageable);

    // 카테고리별 처음 요청
    Page<Post> findByCategoryAndStatusOrderByIdDesc(Category category, ContentStatus status,Pageable pageable);

    // 카테고리별 무한 스크롤
    Page<Post> findByCategoryAndStatusAndIdLessThanOrderByIdDesc( Category category, ContentStatus status,Long lastPostId, Pageable pageable);

    // 카테고리별 게시글 수
    @Query("SELECT COUNT(p) FROM Post p WHERE (:category IS NULL OR p.category = :category)")
    Long countPostByCategoryAndStatus(@Param("category") Category category,ContentStatus status);

    //user의 게시글 수
    @Query("SELECT  COUNT(p) FROM Post p WHERE p.author.id= :authorId ")
    int countPostByAuthorId(@Param("authorId") Long authorId);
    //제목으로 검색
    Page<Post> findByStatusAndTitleContainingAndCreatedAtAfterOrderByIdDesc(ContentStatus status,String title, LocalDateTime startDate, Pageable pageable);
    Page<Post> findByStatusAndTitleContainingAndCreatedAtAfterAndIdLessThanOrderByIdDesc(ContentStatus status,String title, LocalDateTime startDate, Long cursor, Pageable pageable);
    //내용으로 검색
    Page<Post> findByStatusAndContentContainingAndCreatedAtAfterOrderByIdDesc(ContentStatus status,String title, LocalDateTime startDate, Pageable pageable);
    Page<Post> findByStatusAndContentContainingAndCreatedAtAfterAndIdLessThanOrderByIdDesc(ContentStatus status,String title, LocalDateTime startDate, Long cursor, Pageable pageable);


}
