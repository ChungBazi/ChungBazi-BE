package chungbazi.chungbazi_be.domain.community.repository;

import chungbazi.chungbazi_be.domain.community.entity.Post;
import chungbazi.chungbazi_be.domain.policy.entity.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 전체 게시글 처음 요청
    List<Post> findTopByOrderByIdDesc(int size);

    // 전체 게시글 무한 스크롤
    List<Post> findByIdLessThanOrderByIdDesc(Long lastPostId, int size);

    // 카테고리별 처음 요청
    List<Post> findByCategoryOrderByIdDesc(Category category, int size);

    // 카테고리별 무한 스크롤
    List<Post> findByCategoryAndIdLessThanOrderByIdDesc(Category category, Long lastPostId, int size);
}
