package chungbazi.chungbazi_be.domain.community.repository;

import chungbazi.chungbazi_be.domain.community.entity.Post;
import chungbazi.chungbazi_be.domain.policy.entity.Category;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 전체 게시글 처음 요청
    Page<Post> findByOrderByIdDesc(Pageable pageable);

    // 전체 게시글 무한 스크롤
    Page<Post> findByIdLessThanOrderByIdDesc(Long lastPostId, Pageable pageable);

    // 카테고리별 처음 요청
    Page<Post> findByCategoryOrderByIdDesc(Category category, Pageable pageable);

    // 카테고리별 무한 스크롤
    Page<Post> findByCategoryAndIdLessThanOrderByIdDesc(Category category, Long lastPostId, Pageable pageable);
}
